package Vue;

import Dao.DaoFactory;
import Modele.*;
import Dao.CommanderDAOImpl;


import java.util.ArrayList;
import java.util.List;

public class VueCommander {
    public void afficherCommande(Client client, Commander achat,DaoFactory dao) {

        CommanderDAOImpl commanderDAO = new CommanderDAOImpl(dao);  // Crée une instance de CommanderDAOImpl

        List<Article> articles = commanderDAO.getArticlesCommande(achat); //recupere tous les articles d'une commande


        // Afficher les informations du client et du produit pour l'objet achat en paramètre
        System.out.println("Id Client : " + client.getId() + " Nom : " + client.getName()
                + " commande note : " + achat.getNote());

        // Afficher tous les articles associés à la commande
        for (Article article : articles) {
            System.out.println("Nom du produit : " + article.getArticleNom()
                    + " | Prix groupe: " + article.getArticlePrixGroupe()
                    + " | Prix unite: " + article.getArticlePrixUnite()
                    + " | Quantité : " + commanderDAO.getQuantiteArticleFromCommande(achat, article));
        }
    }

    /**
     * Méthode qui affiche la liste des commandes
     * @param achats liste des produits et dao objet de la classe DaoFactory
     */

    public void afficherListeCommandes(Client client, ArrayList<Commander> achats, DaoFactory dao) {
        // Afficher la liste des produits
        for (Commander achat : achats) {
            //afficherCommande(achat);
            afficherCommande(client, achat, dao);
        }
    }
}
