package Controleur;

import Dao.ClientDAO;
import Modele.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import Dao.*;

public class ClientController {

    @FXML
    private Label nomLabel;

    private Main mainApp;
    private ClientDAO clientDAO;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        this.clientDAO = new ClientDAOImpl(mainApp.getConnexion());
    }

    public void initClientData() {
        Client client = mainApp.getClientConnecte();
        if (client != null) {
            nomLabel.setText("Bonjour, " + client.getName());
        }
    }

    @FXML
    private void changerNom() {
        Client client = mainApp.getClientConnecte();
        TextInputDialog dialog = new TextInputDialog(client.getName());
        dialog.setTitle("Changer le nom");
        dialog.setHeaderText("Entrez un nouveau nom :");
        dialog.setContentText("Nouveau nom :");

        dialog.showAndWait().ifPresent(newNom -> {
            if (!newNom.trim().isEmpty()) {
                clientDAO.changerNomClient(client, newNom);
                client.setName(newNom);
                nomLabel.setText("Bonjour, " + newNom);
                showConfirmation("Votre nom a bien été mis à jour.");
            }
        });
    }

    @FXML
    private void changerMDP() {
        Client client = mainApp.getClientConnecte();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Changer le mot de passe");
        dialog.setHeaderText("Entrez un nouveau mot de passe :");
        dialog.setContentText("Nouveau mot de passe :");

        dialog.showAndWait().ifPresent(newMdp -> {
            if (!newMdp.trim().isEmpty()) {
                clientDAO.changerMDPClient(client, newMdp);
                showConfirmation("Votre mot de passe a été changé.");
            }
        });
    }

    @FXML
    private void voirCommandes() {
        try {
            mainApp.switchToCommandeClient(mainApp.getClientConnecte());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void retourPageAccueil() {
        try {
            mainApp.switchToPageAccueil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deconnexion() {
        try {
            mainApp.showConnexionView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
