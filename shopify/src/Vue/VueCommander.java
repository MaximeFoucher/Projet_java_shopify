package Vue;

import Dao.DaoFactory;
import Modele.*;
import Dao.CommanderDAOImpl;


import java.util.ArrayList;
import java.util.List;

public class VueCommander {
    public void afficherCommande(Commander achat, DaoFactory dao) {
        // Récupérer un clientID du getter getClientId de l'objet achat

        CommanderDAOImpl commanderDAO = new CommanderDAOImpl(dao);  // Crée une instance de CommanderDAOImpl
        int idClient = commanderDAO.getIdClient(achat);

        Client client = new Client(idClient); // recuperer le client avec son id
        List<Article> articles = commanderDAO.getArticlesCommande(achat); //recupere tous les articles d'une commande


        // Afficher les informations du client et du produit pour l'objet achat en paramètre
        System.out.println("Id Client : " + idClient + " Nom : " + client.getclientNom()
                + " commande note : " + achat.getNote());

        // Afficher tous les articles associés à la commande
        for (Article article : articles) {
            System.out.println("Nom du produit : " + article.getProduitNom()
                    + " | Prix : " + article.getProduitPrix()
                    + " | Quantité : " + article.getQuantite());
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
