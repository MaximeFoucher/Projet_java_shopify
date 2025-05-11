package Controleur;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import Modele.*;
import Controleur.*;
import javafx.scene.layout.HBox;


import java.util.ArrayList;

public class PageAccueilController {

    @FXML
    private TableView<Article> articleTable;

    @FXML
    private TableColumn<Article, String> colNom;

    @FXML
    private TableColumn<Article, String> colMarque;

    @FXML
    private TableColumn<Article, Double> colPrix;

    @FXML
    private TableColumn<Article, Void> colAjouter;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        afficherArticles();
    }

    private void afficherArticles() {
        // Lier les colonnes aux attributs de Article
        colNom.setCellValueFactory(new PropertyValueFactory<>("articleNom"));
        colMarque.setCellValueFactory(new PropertyValueFactory<>("articleMarque"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("articlePrixUnite"));

        // Colonne bouton "Ajouter au panier"
        colAjouter.setCellFactory(getAjouterCellFactory());

        // Récupération des articles depuis la BDD via le DAO
        ArrayList<Article> articles = mainApp.getDaoFactory().getArticleDao().getAll();
        articleTable.getItems().setAll(articles);
    }

    private Callback<TableColumn<Article, Void>, TableCell<Article, Void>> getAjouterCellFactory() {
        return param -> new TableCell<>() {

            private final Spinner<Integer> spinner = new Spinner<>(1, 100, 1); // Min:1, Max:100, Default:1
            private final Button btnAjouter = new Button("Ajouter");
            private final HBox box = new HBox(5, spinner, btnAjouter);

            {
                btnAjouter.setOnAction(event -> {
                    Article article = getTableView().getItems().get(getIndex());
                    int quantiteChoisie = spinner.getValue();

                    if (quantiteChoisie > article.getArticleStock()) {
                        showAlert("Stock insuffisant", "Il n'y a pas assez de stock disponible.");
                        return;
                    }

                    try {
                        // Tu dois avoir une commande active à récupérer
                        //Commander commande = mainApp.getCommandeActive(); // à créer ou à stocker globalement
                        Client client = mainApp.getClientConnecte();
                        Commander commande = mainApp.getCommandeDAO().getCommanderFromClient(client);
                        mainApp.getCommandeDAO().ajouterArticleDansCommande(commande, article, client, quantiteChoisie);

                        // Mettre à jour localement le stock de l'article
                        article.setArticleStock(article.getArticleStock() - quantiteChoisie);
                        articleTable.refresh(); // Rafraîchit la table

                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert("Erreur", "Impossible d'ajouter l'article au panier.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(box);
                }
            }

            private void showAlert(String titre, String msg) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(titre);
                alert.setContentText(msg);
                alert.showAndWait();
            }
        };
    }


    @FXML
    private void handleVoirProfil() {
        try{
            mainApp.switchToClientView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Redirection vers le profil utilisateur");
    }

    @FXML
    private void handleVoirCommandes() {
        try {
            mainApp.switchToCommandeClient(mainApp.getClientConnecte());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Redirection vers la section Commande");
    }

    @FXML
    private void handleLogout() {
        try {
            mainApp.showConnexionView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoirPanier(){
        try {
            mainApp.switchToPanier();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
