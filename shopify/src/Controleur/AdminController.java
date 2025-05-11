package Controleur;

import Dao.*;
import Modele.*;
import Controleur.*;
import com.sun.jdi.connect.spi.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class AdminController {

    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, String> colNomClient;
    @FXML private TableColumn<Client, String> colEmailClient;
    @FXML private TableColumn<Client, Boolean> colAdminClient;

    @FXML private TableView<Article> articleTable;
    @FXML private TableColumn<Article, String> colNomArticle;
    @FXML private TableColumn<Article, String> colMarqueArticle;
    @FXML private TableColumn<Article, Double> colPrixUniteArticle;

    private ClientDAO clientDAO;
    private ArticleDao articleDAO;
    private DaoFactory daoFactory;
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void initDonnees() {
        // Initialisation des DAO une fois mainApp défini
        this.clientDAO = new ClientDAOImpl(mainApp.getConnexion());
        this.articleDAO = new ArticleDaoImpl(mainApp.getDaoFactory());

        // Initialisation colonnes client
        System.out.println("colNomClient est null ? " + (colNomClient == null));
        colNomClient.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colEmailClient.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colAdminClient.setCellValueFactory(new PropertyValueFactory<>("Admin"));

        // Initialisation colonnes article
        colNomArticle.setCellValueFactory(new PropertyValueFactory<>("articleNom"));
        colMarqueArticle.setCellValueFactory(new PropertyValueFactory<>("articleMarque"));
        colPrixUniteArticle.setCellValueFactory(new PropertyValueFactory<>("articlePrixUnite"));

        chargerClients();
        chargerArticles();
    }

    private void chargerClients() {
        ObservableList<Client> clients = FXCollections.observableArrayList(clientDAO.ToutLister());
        clientTable.setItems(clients);
    }

    private void chargerArticles() {
        ObservableList<Article> articles = FXCollections.observableArrayList(articleDAO.getAll());
        articleTable.setItems(articles);
    }

    @FXML
    private void handleChangerNomClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TextInputDialog dialog = new TextInputDialog(selected.getName());
            dialog.setHeaderText("Nouveau nom :");
            dialog.showAndWait().ifPresent(newNom -> {
                clientDAO.changerNomClient(selected, newNom);
                chargerClients();
            });
        }
    }

    @FXML
    private void handleChangerEmailClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TextInputDialog dialog = new TextInputDialog(selected.getEmail());
            dialog.setHeaderText("Nouvel email :");
            dialog.showAndWait().ifPresent(newEmail -> {
                clientDAO.changerEmailClient(selected, newEmail);
                chargerClients();
            });
        }
    }

    @FXML
    private void handleChangerMDPClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Nouveau mot de passe :");
            dialog.showAndWait().ifPresent(newMdp -> {
                clientDAO.changerMDPClient(selected, newMdp);
            });
        }
    }

    @FXML
    private void handleSupprimerClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            clientDAO.SupprimerClient(selected);
            chargerClients();
        }
    }

    @FXML
    private void handleToggleAdmin() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            clientDAO.switchStatutAdmin(selected);
            chargerClients();
        }
    }

    @FXML
    private void handleAjouterArticle() {

    }

    @FXML
    private void handleModifierArticle() {

    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleSupprimerArticle() {
        Article selected = articleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            articleDAO.supprimer(selected);
            chargerArticles();
        }
    }
}

