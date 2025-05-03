package Controleur;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import Dao.ClientDAOImpl;
import Modele.Client;
import java.sql.Connection;

public class ConnexionController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private Main mainApp;
    private ClientDAOImpl clientDAO;

    // Appelée par le Main après chargement du FXML
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Création de la DAO avec la connexion partagée
        Connection conn = mainApp.getConnexion(); // ajoute cette méthode dans Main si nécessaire
        this.clientDAO = new ClientDAOImpl(conn);
    }

    @FXML
    private void seConnecter() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            if (email.equals("admin") && password.equals("admin")) {
                mainApp.switchToAdminView();
                return;
            }

            // Recherche du client par email
            Client client = (Client) clientDAO.RechercheEmail(email);

            if (client != null && client.getMdp().equals(password)) {
                mainApp.setClientConnecte(client); // stocke le client
                mainApp.switchToClientView();      // va sur la vue client
            } else {
                showAlert("Échec de la connexion", "Email ou mot de passe incorrect.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void creerCompte() {
        try {
            mainApp.showCreerCompteView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
