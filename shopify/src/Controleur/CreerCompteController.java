package Controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Modele.Client;
import Dao.ClientDAOImpl;
import Dao.DaoFactory;

public class CreerCompteController {

    @FXML private TextField nomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void validerCreation() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String mdp = passwordField.getText();

        if (nom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        Client nouveau = new Client(nom, email, 0, mdp);
        ClientDAOImpl dao = new ClientDAOImpl(mainApp.getConnexion()); // ou mainApp.getConnexion() si tu préfères

        dao.ajouter(nouveau);
        showAlert("Succès", "Compte créé avec succès !");
        try {
            mainApp.showConnexionView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void annuler() {
        try {
            mainApp.showConnexionView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
