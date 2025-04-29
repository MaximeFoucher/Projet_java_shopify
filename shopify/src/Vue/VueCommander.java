package Vue;

import Dao.CommanderDAO;
import Dao.DaoFactory;
import Modele.Commander;
import Dao.CommanderDAOImpl;
import Dao.CommanderDAO;
import Modele.*;
import Modele.Article;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VueCommander {
    public void afficherCommande(Commander achat, DaoFactory dao) {
        // Récupérer un clientID du getter getClientId de l'objet achat

        CommanderDAOImpl commanderDAO = new CommanderDAOImpl(dao);  // Crée une instance de CommanderDAOImpl
        int idClient = commanderDAO.getIdClient(achat);
        // recuperer tous les attribus pour faire le client donc nom et psw

        Clients client = new clients(idClient); // recuperer le client avec son id
        List<Article> articles = commanderDAO.getArticlesCommande(achat); //recupere tous les articles d'une commande

        // Afficher les informations du client et du produit pour l'objet achat en paramètre
        System.out.println("Id Client : " + idClient + " Nom : " + client.getName()
                + " commande note : " + achat.getNote());

        // Afficher tous les articles associés à la commande
        for (Article article : articles) {
            System.out.println("Nom du produit : " + article.getArticleNom()
                    + " | Prix : " + article.getArticlePrixUnite()
                    + " | Quantité : " + commanderDAO.getArticleQuantite(achat, article)); //rajouter la quantité donc join article a commande pour avoir la
        }
    }

    /**
     * Méthode qui affiche la liste des commandes
     * @param achats liste des produits et dao objet de la classe DaoFactory
     */

    public void afficherListeCommandes(ArrayList<Commander> achats, DaoFactory dao) {
        // Afficher la liste des produits
        for (Commander achat : achats) {
            //afficherCommande(achat);
            afficherCommande(achat, dao);
        }
    }
}
