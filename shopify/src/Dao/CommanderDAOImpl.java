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
    public boolean ajouterCommande(Commander commande, Profil profil) {
        //ajoute une commande si le panier à deja ete reglé
        try (Connection connexion = daoFactory.getConnection()) {
            // Vérifier s'il existe déjà une commande non payée avec l'id du profil
            String checkSql = "SELECT COUNT(*) FROM profil p " +
                    "JOIN historique h ON p.Id = h.Id_profil " +
                    "JOIN commande c ON h.Id_commande = c.Id_commande " +
                    "WHERE p.Id = ? " +
                    "AND c.paye = false";
            PreparedStatement checkStmt = connexion.prepareStatement(checkSql);
            checkStmt.setInt(1, profil.getId()); //prend l'id de profil
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    System.out.println("Il existe déjà une commande non payée. Impossible d'ajouter une nouvelle commande. Il faut regler le panier");
                    return false;
                }
            }

            // Sinon, ajouter la nouvelle commande et le reste est en AI ou valeur par defaut
            String sql = "INSERT INTO commande (note) VALUES (?)";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, commande.getNote());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout d'une commande : " + e.getMessage());
            return false;
        }
    }


    @Override
    public List<Commander> getCommandesClient(Profil profil) {
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
            stmt.setInt(1, profil.getId()); //id du client
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

}
