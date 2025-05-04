package Controleur;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import Modele.Client;
import Modele.Commander;

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
            commandesList.getItems().add("Id : " + cmd.getCommandeId() + "Note : " + cmd.getNote() + "Payé : " + cmd.getPaye());
        }
    }

    @FXML
    private void retour() {
        try {
            mainApp.switchToClientView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}