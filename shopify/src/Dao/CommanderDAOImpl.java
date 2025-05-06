package Dao;

import Modele.*;
//import Modele.Profil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommanderDAOImpl implements CommanderDAO {
    private DaoFactory daoFactory;

    public CommanderDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouterCommande(Client client) {
        //ajoute une commande si le panier à deja ete reglé
        try (Connection connexion = daoFactory.getConnection()) {


                /// ajouter la nouvelle commande et le reste est en AI ou valeur par defaut
                String sql = "INSERT INTO commande (note) VALUES (?)";
                PreparedStatement stmt = connexion.prepareStatement(sql);
                stmt.setInt(1, 0);

                stmt.executeUpdate();



        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout d'une commande : " + e.getMessage());
        }
    }

    @Override
    public List<Commander> getCommandesClient(Client client) {
        // avoir toutes les commandes du client et le panier
        List<Commander> commandes = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection()){

            String sql = "SELECT c.Id, c.Note, c.Payé, i.Quanite, i.Prix, a.Nom, a.Marque " +
                    "FROM profil p " +
                    "JOIN historique h ON p.Id = h.Id_profil " +
                    "JOIN commande c ON h.Id_commande = c.Id " +
                    "JOIN item i ON c.Id = i.Id_commande " +
                    "JOIN article a ON a.Id = i.Id_article " +
                    "WHERE p.Id = ? ";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, client.getId()); //id du client
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int commandeId = rs.getInt("Id");
                int note = rs.getInt("Note");
                boolean paye = rs.getBoolean("Payé");

                int quantite = rs.getInt("Quantite");
                double prix = rs.getDouble("Prix");
                String nomArticle = rs.getString("Nom");
                String marqueArticle = rs.getString("Marque");

                Commander commande = new Commander(commandeId, note, paye);

                commandes.add(commande);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des commandes : " + e.getMessage());
        }
        return commandes;
    }

    @Override
    public int getIdClient(Commander commande) {
        int idClient = -1; // valeur par défaut si pas trouvé

        try (Connection connexion = daoFactory.getConnection()) {
            String checkSql = "SELECT p.Id FROM profil p " +
                    "JOIN historique h ON p.Id = h.Id_profil " +
                    "JOIN commande c ON h.Id_commande = c.Id " +
                    "WHERE c.Id = ?";

            PreparedStatement checkStmt = connexion.prepareStatement(checkSql);
            checkStmt.setInt(1, commande.getCommandeId()); // on met l'id de la commande
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                idClient = rs.getInt("Id"); // on récupère l'id du client
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du client : " + e.getMessage());
        }

        return idClient;
    }

    @Override
    public Client getClientFromCommande(Commander commande) {
        Client client = null;

        try (Connection connexion = daoFactory.getConnection()) {
            String sql = "SELECT p.Id, p.Mdp, p.Nom, p.Email " +
                    "FROM profil p " +
                    "JOIN historique h ON p.Id = h.Id_profil " +
                    "JOIN commande c ON h.Id_commande = c.Id " +
                    "WHERE c.Id = ?";

            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, commande.getCommandeId());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("Id");
                String mdp = rs.getString("Mdp");
                String nom = rs.getString("Nom");
                String email = rs.getString("Email");

                client = new Client(nom, email, id, mdp); // appel du constructeur Client
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du client : " + e.getMessage());
        }

        return client;
    }

    @Override
    public int getQuantiteArticleFromCommande(Commander commande, Article article) {
        int quantiteArticle = -1;

        try (Connection connexion = daoFactory.getConnection()) {
            String sql = "SELECT Quantite FROM item WHERE Id_commande = ? AND Id_article = ?";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, commande.getCommandeId());
            stmt.setInt(2, article.getArticleId());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                quantiteArticle = rs.getInt("Quantite");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la quantité de l'article : " + e.getMessage());
        }

        return quantiteArticle;
    }

    @Override
    public List<Article> getArticlesCommande(Commander commande) {
        // avoir tous les articles liés à une commande
        List<Article> articles = new ArrayList<>();

        try (Connection connexion = daoFactory.getConnection()) {
            String checkSql = "SELECT a.Id, a.Marque, a.Nom, a.Prix_unite, a.Prix_groupe, a.valeur_lot, a.stock FROM article a " +
                    "JOIN item i ON a.Id = i.Id_article " +
                    "JOIN commande c ON i.Id_commande = c.Id " +
                    "WHERE c.Id = ?";

            PreparedStatement checkStmt = connexion.prepareStatement(checkSql);
            checkStmt.setInt(1, commande.getCommandeId()); // on met l'id de la commande
            ResultSet rs = checkStmt.executeQuery();

            while (rs.next()) {
                int articleId = rs.getInt("Id");
                String marque = rs.getString("Marque");
                String nom = rs.getString("Nom");
                double prix_unit = rs.getDouble("Prix_unite");
                double prix_grp = rs.getDouble("Prix_groupe");
                int valeur_lot = rs.getInt("valeur_lot");
                double stock = rs.getDouble("stock");

                //int articleId, String articleMarque, String articleNom,
                // double articlePrixUnite, double articlePrixGroupe, int articleValeurLot, double articleStock

                Article article = new Article(articleId, marque, nom, prix_unit, prix_grp, valeur_lot, stock); //verifier si on peut faire un nouvel article avec seulement son id

                articles.add(article);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des articles : " + e.getMessage());
        }

        return articles;
    }

    @Override
    public boolean VerifierExistancePanier(Commander commande, Client client) {
        try (Connection connexion = daoFactory.getConnection()) {
            /// verification qu'un panier est en cours
            /// return True si le panier existe, False sinon

            String checkSql = "SELECT COUNT(*) FROM profil p " +
                    "JOIN historique h ON p.Id = h.Id_profil " +
                    "JOIN commande c ON h.Id_commande = c.Id_commande " +
                    "WHERE p.Id = ? " +
                    "AND c.paye = false";
            PreparedStatement checkStmt = connexion.prepareStatement(checkSql);
            checkStmt.setInt(1, client.getId()); //prend l'id de profil
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    System.out.println("Il existe déjà une commande non payée. Impossible d'ajouter une nouvelle commande. Il faut regler le panier");
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la verification panier : " + e.getMessage());

        }
        return false;

    }

    public void MAJTableCommande(Commander commande) {
        try (Connection connexion = daoFactory.getConnection()) {

            // Étape 1 : Récupérer la somme des Notes des items
            String sql = "SELECT SUM(Note) FROM item WHERE Id_commande = ?";
            int totalNote = 0;

            try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
                stmt.setInt(1, commande.getCommandeId());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    totalNote = rs.getInt(1);
                }
            }

            // Étape 2 : Mettre à jour la Note dans la table commande
            String updateSql = "UPDATE commande SET Note = ? WHERE Id_commande = ?";
            try (PreparedStatement updateStmt = connexion.prepareStatement(updateSql)) {
                updateStmt.setInt(1, totalNote);
                updateStmt.setInt(2, commande.getCommandeId());
                updateStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la table commande : " + e.getMessage());
        }
    }

    @Override
    public void ajouterArticleDansCommande(Commander commande, Article article, Client client, int quantiteArticle) {
        try (Connection connexion = daoFactory.getConnection()) {

            // Récupérer les infos sur l'article
            int prix_unite = 0;
            int prix_groupe = 0;
            int valeur_lot = 0;
            int prix_total = 0;

            String sqlArticle = "SELECT Prix_unite, Prix_groupe, Quantite_groupe FROM article WHERE Id_article = ?";
            PreparedStatement stmtArticle = connexion.prepareStatement(sqlArticle);
            stmtArticle.setInt(1, article.getArticleId());
            ResultSet rsArticle = stmtArticle.executeQuery();
            if (rsArticle.next()) {
                prix_unite = rsArticle.getInt("Prix_unite");
                prix_groupe = rsArticle.getInt("Prix_groupe");
                valeur_lot = rsArticle.getInt("Quantite_groupe");

                if (valeur_lot > 0 && prix_groupe > 0 && quantiteArticle >= valeur_lot) {
                    int nbGroupes = quantiteArticle / valeur_lot;
                    int reste = quantiteArticle % valeur_lot;
                    prix_total = nbGroupes * prix_groupe + reste * prix_unite;
                } else {
                    prix_total = quantiteArticle * prix_unite;
                }
            }

            // Vérifier si l'article est déjà dans la commande
            String checkSql = "SELECT Quantite FROM item WHERE Id_article = ? AND Id_commande = ?";
            PreparedStatement checkStmt = connexion.prepareStatement(checkSql);
            checkStmt.setInt(1, article.getArticleId());
            checkStmt.setInt(2, commande.getCommandeId());
            ResultSet rsCheck = checkStmt.executeQuery();

            if (rsCheck.next()) {
                // Déjà présent → on met à jour la quantité et le prix
                int quantiteExistante = rsCheck.getInt("Quantite");
                int nouvelleQuantite = quantiteExistante + quantiteArticle;

                // Recalcul du prix total avec la nouvelle quantité
                int nouveauPrix = 0;
                if (valeur_lot > 0 && prix_groupe > 0 && nouvelleQuantite >= valeur_lot) {
                    int nbGroupes = nouvelleQuantite / valeur_lot;
                    int reste = nouvelleQuantite % valeur_lot;
                    nouveauPrix = nbGroupes * prix_groupe + reste * prix_unite;
                } else {
                    nouveauPrix = nouvelleQuantite * prix_unite;
                }

                String updateSql = "UPDATE item SET Quantite = ?, Prix = ? WHERE Id_article = ? AND Id_commande = ?";
                PreparedStatement updateStmt = connexion.prepareStatement(updateSql);
                updateStmt.setInt(1, nouvelleQuantite);
                updateStmt.setInt(2, nouveauPrix);
                updateStmt.setInt(3, article.getArticleId());
                updateStmt.setInt(4, commande.getCommandeId());
                updateStmt.executeUpdate();
            } else {
                // Nouvel article → on l’insère
                String insertSql = "INSERT INTO item (Id_article, Id_commande, Quantite, Prix) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = connexion.prepareStatement(insertSql);
                insertStmt.setInt(1, article.getArticleId());
                insertStmt.setInt(2, commande.getCommandeId());
                insertStmt.setInt(3, quantiteArticle);
                insertStmt.setInt(4, prix_total);
                insertStmt.executeUpdate();
            }

            // Mettre à jour le total de la commande
            MAJTableCommande(commande);

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout d'un article dans la commande : " + e.getMessage());
        }
    }

    @Override
    public void reglerPanier(Commander commande, Client client) {
        /// regler le panier
        try (Connection connexion = daoFactory.getConnection()) {
            /// mettre le boolean à true
            /// faire un nouveau panier
            commande.setPaye();
            ajouterCommande(client);

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout d'un article dans la commande : " + e.getMessage());

        }
    }

    @Override
    public void supprimerCommande(Commander commande){
        try (Connection connexion = daoFactory.getConnection()) {
            /// supprimer les items associés à la commande
            String sql = "DELETE FROM item WHERE Id_commande = ?";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, commande.getCommandeId());
            stmt.executeUpdate();

            /// supprimer la commande
            String deletecommande = "DELETE FROM commande WHERE Id = ?";
            PreparedStatement stmt2 = connexion.prepareStatement(deletecommande);
            stmt2.setInt(1, commande.getCommandeId());
            stmt2.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout d'un article dans la commande : " + e.getMessage());

        }
    }

    @Override
    public void viderPanier(Client client){
        try (Connection connexion = daoFactory.getConnection()) {
            /// recuperer l'id du panier en cours
            /// supprimer les items associés au panier

            /// Récupérer l'ID de la commande non payée liée au client
            String sqlCommande = "SELECT commande.Id FROM commande " +
                    "JOIN historique ON commande.Id = historique.Id_commande " +
                    "JOIN profil ON historique.Id_profil = profil.Id " +
                    "WHERE profil.Id = ? AND commande.Payé = false";
            PreparedStatement stmt = connexion.prepareStatement(sqlCommande);
            stmt.setInt(1, client.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idCommande = rs.getInt("Id");

                /// Supprimer les items liés à cette commande
                String sqlDeleteItems = "DELETE FROM item WHERE Id_commande = ?";
                PreparedStatement deleteStmt = connexion.prepareStatement(sqlDeleteItems);
                deleteStmt.setInt(1, idCommande);
                deleteStmt.executeUpdate();

            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des items dans le panier : " + e.getMessage());

        }
    }

}
