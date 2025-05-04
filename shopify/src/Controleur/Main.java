package Controleur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Dao.*;
import Modele.*;

import java.sql.Connection;
import java.util.List;

public class Main extends Application {

    // 🔌 Accès à la BDD
    private DaoFactory daoFactory;
    private Connection connexion;

    // 👤 Client actuellement connecté
    private Client clientConnecte;

    // 🎬 Fenêtre principale JavaFX
    private Stage stage;

    // ========================================
    // 🚀 Point d’entrée JavaFX
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        // Initialisation de la DAO et de la connexion à la BDD
        this.daoFactory = DaoFactory.getInstance("shopify", "root", "");
        this.connexion = daoFactory.getConnection();

        // Affichage de la première vue : Connexion
        showConnexionView();
    }

    // ========================================
    // 🔄 Navigation entre les vues
    public void showConnexionView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ConnexionView.fxml"));
        Parent root = loader.load();

        ConnexionController controller = loader.getController();
        controller.setMainApp(this);

        stage.setTitle("Connexion");
        stage.setScene(new Scene(root));
        stage.show();
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

    public void switchToClientView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ClientView.fxml"));
        Parent root = loader.load();

        ClientController controller = loader.getController();
        controller.setMainApp(this);
        controller.initClientData(); // Injecte les données du client

        stage.setTitle("Espace Client");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToAdminView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AdminView.fxml"));
        Parent root = loader.load();

        // ⚠ À implémenter plus tard
        stage.setTitle("Espace Admin");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToCommandeClient(Client client) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CommandeView.fxml"));
        Parent root = loader.load();

        CommandeController controller = loader.getController();
        controller.setMainApp(this);
        controller.setClient(client);

        // Récupération des commandes du client
        CommanderDAO commandeDAO = new CommanderDAOImpl(daoFactory);
        List<Commander> commandes = commandeDAO.getCommandesClient(client);
        controller.setCommandes(commandes);

        stage.setTitle("Historique des commandes");
        stage.setScene(new Scene(root));
        stage.show();
    }

    // ========================================
    // 🔧 Getters / Setters utiles dans les controllers

    public Connection getConnexion() {
        return this.connexion;
    }

    public DaoFactory getDaoFactory() {
        return this.daoFactory;
    }

    public void setClientConnecte(Client client) {
        this.clientConnecte = client;
    }

    public Client getClientConnecte() {
        return this.clientConnecte;
    }

    // ========================================
    // 🧠 Méthode principale
    public static void main(String[] args) {
        launch(args);
    }
}
