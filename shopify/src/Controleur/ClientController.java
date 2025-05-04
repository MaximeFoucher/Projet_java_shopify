package Controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import Modele.*;
import Controleur.*;

public class ClientController {

    @FXML private Label nomLabel;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void initClientData() {
        Client client = mainApp.getClientConnecte();
        if (client != null) {
            nomLabel.setText("Bonjour, " + client.getName());
        }
    }

    @FXML
    private void voirCommandes() {
        try{
            mainApp.switchToCommandeClient(mainApp.getClientConnecte());
        } catch (Exception e) {
        e.printStackTrace();
    }
        // À implémenter : changer de page ou afficher un tableau
        System.out.println("→ Affichage des commandes du client");
    }

    @FXML
    private void deconnexion() {
        try {
            mainApp.showConnexionView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
