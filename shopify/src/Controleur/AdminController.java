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
    @FXML private TableColumn<Article, Double> colPrixGroupeArticle;
    @FXML private TableColumn<Article, Integer> colValeurLotArticle;
    @FXML private TableColumn<Article, Integer> colValeurStockArticle;

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
        colPrixGroupeArticle.setCellValueFactory(new PropertyValueFactory<>("articlePrixGroupe"));
        colValeurLotArticle.setCellValueFactory(new PropertyValueFactory<>("articleValeurLot"));
        colValeurStockArticle.setCellValueFactory(new PropertyValueFactory<>("articleStock"));



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
        Dialog<Article> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un article");

        // Création des champs
        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField marqueField = new TextField();
        marqueField.setPromptText("Marque");

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");

        TextField prixUniteField = new TextField();
        prixUniteField.setPromptText("Prix Unité");

        TextField prixGroupeField = new TextField();
        prixGroupeField.setPromptText("Prix Groupe");

        TextField valeurLotField = new TextField();
        valeurLotField.setPromptText("Valeur Lot");

        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        // Mise en page
        VBox content = new VBox(10,
                new Label("ID :"), idField,
                new Label("Nom :"), nomField,
                new Label("Marque :"), marqueField,
                new Label("Prix Unité :"), prixUniteField,
                new Label("Prix Groupe :"), prixGroupeField,
                new Label("Valeur Lot :"), valeurLotField,
                new Label("Stock :"), stockField
        );
        dialog.getDialogPane().setContent(content);

        // Boutons
        ButtonType ajouterButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ajouterButton, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ajouterButton) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String nom = nomField.getText();
                    String marque = marqueField.getText();
                    double prixUnite = Double.parseDouble(prixUniteField.getText());
                    double prixGroupe = Double.parseDouble(prixGroupeField.getText());
                    int valeurLot = Integer.parseInt(valeurLotField.getText());
                    double stock = Double.parseDouble(stockField.getText());

                    return new Article(id, marque, nom, prixUnite, prixGroupe, valeurLot, stock);

                } catch (NumberFormatException e) {
                    showError("Veuillez remplir tous les champs numériques correctement.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(article -> {
            articleDAO.ajouter(article);
            chargerArticles(); // Recharge la liste affichée
        });
    }

    @FXML
    private void handleModifierArticle() {
        Article selected = articleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Dialog<Article> dialog = new Dialog<>();
            dialog.setTitle("Modifier un article");

            TextField nomField = new TextField(selected.getArticleNom());
            TextField marqueField = new TextField(selected.getArticleMarque());
            TextField prixUniteField = new TextField(String.valueOf(selected.getArticlePrixUnite()));
            TextField prixGroupeField = new TextField(String.valueOf(selected.getArticlePrixGroupe()));
            TextField valeurLotField = new TextField(String.valueOf(selected.getArticleValeurLot()));
            TextField stockField = new TextField(String.valueOf(selected.getArticleStock()));


            VBox content = new VBox(10,
                    new Label("Nom :"), nomField,
                    new Label("Marque :"), marqueField,
                    new Label("Prix unité :"), prixUniteField,
                    new Label("Prix Groupe :"), prixGroupeField,
                    new Label("Valeur Lot :"), valeurLotField,
                    new Label("Stock :"), stockField);
            dialog.getDialogPane().setContent(content);

            ButtonType modifierButton = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(modifierButton, ButtonType.CANCEL);

            dialog.setResultConverter(btn -> {
                if (btn == modifierButton) {
                    try {
                        selected.setArticleNom(nomField.getText());
                        selected.setArticleMarque(marqueField.getText());
                        selected.setArticlePrixUnite(Double.parseDouble(prixUniteField.getText()));
                        selected.setArticlePrixGroupe(Double.parseDouble(prixGroupeField.getText()));
                        selected.setArticleValeurLot(Integer.parseInt(valeurLotField.getText()));
                        selected.setArticleStock(Double.parseDouble(stockField.getText()));
                        return selected;
                    } catch (NumberFormatException e) {
                        showError("Erreur dans la saisie.");
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(article -> {
                articleDAO.modifier(article);
                chargerArticles();
            });
        }
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

