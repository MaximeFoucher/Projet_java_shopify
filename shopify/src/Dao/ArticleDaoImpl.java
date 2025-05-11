package Dao;

import Modele.Article;
import java.sql.*;
import java.util.ArrayList;

public class ArticleDaoImpl implements ArticleDao {

    private DaoFactory daoFactory;

    public ArticleDaoImpl(DaoFactory daoFactory) {this.daoFactory = daoFactory;}

    @Override
    public ArrayList<Article> getAll() {
        ArrayList<Article> listeArticles = new ArrayList<Article>();

        try {
            /// connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("select * from article");

            while (resultats.next()) {

                /// récupérer les 7 champs de la table produits dans la base de données
                int articleId = resultats.getInt(1);
                String articleMarque = resultats.getString(2);
                String articleNom = resultats.getString(3);
                double articlePrixUnite = resultats.getDouble(4);
                double articlePrixGroupe = resultats.getDouble(5);
                int articleValeurLot = resultats.getInt(6);
                double articleStock = resultats.getDouble(7);

                /// instancier un objet de Article avec ces 7 champs en paramètres
                Article Article = new Article(articleId,articleMarque, articleNom , articlePrixUnite, articlePrixGroupe, articleValeurLot, articleStock);

                /// ajouter ce produit à listeProduits
                listeArticles.add(Article);
            }
        }
        catch (SQLException e) {
            /// traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste d'Articles impossible");
        }

        return listeArticles;
    }

    @Override
    public Article chercher(int id) {
        Article article = null;

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // Exécution de la requête SELECT pour récupérer le article de l'id dans la base de données
            ResultSet resultats = statement.executeQuery("select * from article where articleID="+id);

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                int articleId = resultats.getInt(1);
                String articleMarque = resultats.getString(2);
                String articleNom = resultats.getString(3);
                double articlePrixUnite = resultats.getDouble(4);
                double articlePrixGroupe = resultats.getDouble(5);
                int articleValeurLot = resultats.getInt(6);
                double articleStock = resultats.getDouble(7);

                // Si l'id du article est trouvé, l'instancier et sortir de la boucle
                if (id == articleId) {
                    article = new Article(articleId,articleMarque, articleNom, articlePrixUnite, articlePrixGroupe, articleValeurLot, articleStock);
                    break;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("article non trouvé dans la base de données");
        }

        return article;
    }

    @Override
    public void ajouter(Article article) {
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            String sql = "INSERT INTO article (Id, Marque, Nom, Prix_unite, Prix_groupe, valeur_lot, stock) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setInt(1,article.getArticleId());
            preparedStatement.setString(2,article.getArticleMarque());
            preparedStatement.setString(3,article.getArticleNom());
            preparedStatement.setDouble(4,article.getArticlePrixUnite());
            preparedStatement.setDouble(5,article.getArticlePrixGroupe());
            preparedStatement.setInt(6,article.getArticleValeurLot());
            preparedStatement.setDouble(7,article.getArticleStock());

            preparedStatement.execute();
            preparedStatement.close();
            connexion.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("article non trouvé dans la base de données");
        }

        }

    @Override
    public Article modifier(Article article) {
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();

            String sql = "UPDATE article SET Marque = ?, Nom = ?, Prix_unite = ?, Prix_groupe = ?, valeur_lot = ?, stock = ? WHERE Id = ?";

            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1,article.getArticleMarque());
            preparedStatement.setString(2,article.getArticleNom());
            preparedStatement.setDouble(3,article.getArticlePrixUnite());
            preparedStatement.setDouble(4,article.getArticlePrixGroupe());
            preparedStatement.setInt(5,article.getArticleValeurLot());
            preparedStatement.setDouble(6,article.getArticleStock());
            preparedStatement.setInt(7,article.getArticleId());

            preparedStatement.execute();
            preparedStatement.close();
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("article non trouvé dans la base de données");
        }
        return article;
    }

    @Override
    public void supprimer(Article article) {
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();

            String sql = "DELETE FROM article WHERE articleID = ?";

            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setInt(1,article.getArticleId());
            preparedStatement.execute();
            preparedStatement.close();
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("article non trouvé dans la base de données");
        }
    }
}

