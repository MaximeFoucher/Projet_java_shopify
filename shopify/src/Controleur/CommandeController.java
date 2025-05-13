package Controleur;

import Modele.Article;
import Modele.Client;
import Modele.Commander;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class CommandeController {

    @FXML
    private ListView<String> commandesList;

    private Main mainApp;
    private Client client;
    private List<Commander> commandes;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCommandes(List<Commander> commandes) {
        this.commandes = commandes;
        afficherCommandes();
    }

    private void afficherCommandes() {
        commandesList.getItems().clear();
        for (Commander cmd : commandes) {
            commandesList.getItems().add(
                    String.format("Commande #%d | Total : %.2f €",
                            cmd.getCommandeId(),
                            (double) cmd.getNote())
            );
        }
    }

    @FXML
    private void retour() {
        try {
            mainApp.switchToPageAccueil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
