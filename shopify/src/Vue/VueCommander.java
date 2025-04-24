package Vue;

import Dao.CommanderDAO;
import Dao.DaoFactory;
import Modele.Commander;



import java.util.Scanner;

public class VueCommander {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommanderDAO dao = DaoFactory.getCommanderDAO();

        System.out.println("=== Passer une commande ===");
        System.out.print("ID Client : ");
        int clientId = scanner.nextInt();
        System.out.print("ID Produit : ");
        int produitId = scanner.nextInt();
        System.out.print("Quantité : ");
        int quantite = scanner.nextInt();

        Commander commande = new Commander(clientId, produitId, quantite);
        boolean success = dao.ajouterCommande(commande);

        if (success) {
            System.out.println("✅ Commande ajoutée avec succès !");
        } else {
            System.out.println("❌ Échec de l’ajout de la commande.");
        }
    }
}
