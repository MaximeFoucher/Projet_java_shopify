package Controleur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Modele.*;
import Dao.*;

import java.sql.Connection;

public class Main extends Application {
    private Connection connexion;         // Connexion JDBC à ta base de données
    private Client clientConnecte;        // Le client actuellement connecté

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        DaoFactory dao = DaoFactory.getInstance("shopify", "root", "");
        this.connexion = dao.getConnection();
        showConnexionView();
    }

    public void showConnexionView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ConnexionView.fxml"));
        Parent root = loader.load();

        // Lier le contrôleur à l'application principale
        ConnexionController controller = loader.getController();
        controller.setMainApp(this);

        stage.setTitle("Connexion");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToAdminView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AdminView.fxml"));
        // Exemple provisoire
        System.out.println("Redirection vers l'interface Admin (à implémenter)");
        // Tu mettras ici le code pour charger AdminView.fxml quand tu l'auras
    }

    public void switchToClientView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ClientView.fxml"));
        Parent root = loader.load();

        // Récupération du contrôleur et injection de l'application principale
        ClientController controller = loader.getController();
        controller.setMainApp(this); // Permet au contrôleur de revenir vers Main
        controller.initClientData(); // Optionnel : initialise les infos client

        stage.setTitle("Espace Client");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public Connection getConnexion() {
        return this.connexion;
    }

    public void setClientConnecte(Client client) {
        this.clientConnecte = client;
    }
    public Client getClientConnecte() {
        return this.clientConnecte;
    }

    public void showCreerCompteView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CreerCompteView.fxml"));
        Parent root = loader.load();
        CreerCompteController controller = loader.getController();
        controller.setMainApp(this);
        stage.setTitle("Créer un compte");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
