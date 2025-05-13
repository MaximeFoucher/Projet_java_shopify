package Controleur;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import Dao.ClientDAOImpl;
import Dao.AdminDAOImpl;
import Modele.*;
import java.sql.Connection;

public class ConnexionController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private Client client;
    private Main mainApp;
    private ClientDAOImpl clientDAO;
    private AdminDAOImpl adminDAO;

    // Appelée par le Main après chargement du FXML
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Création de la DAO avec la connexion partagée
        Connection conn = mainApp.getConnexion(); // ajoute cette méthode dans Main si nécessaire
        this.clientDAO = new ClientDAOImpl(conn);
        this.adminDAO = new AdminDAOImpl(conn);
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
            Admin admin = (Admin) adminDAO.RechercheEmail(email);
            if (admin != null && admin.getMdp().equals(password)) {
                mainApp.switchToAdminView();
                return;
            }

            // Recherche du client par email
            Client client = (Client) clientDAO.RechercheEmail(email);

            if (client != null && client.getMdp().equals(password)) {
                mainApp.setClientConnecte(client); // stocke le client
                Commander panier = mainApp.getCommandeDAO().getPanierActif(client);
                mainApp.setCommandeActive(panier);
                mainApp.switchToPageAccueil();      // va sur la page d'accueil
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
