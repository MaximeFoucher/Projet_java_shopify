package Controleur;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import Modele.Article;
import Controleur.Main;

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
            private final Button btn = new Button("Ajouter");

            {
                btn.setOnAction(event -> {
                    Article article = getTableView().getItems().get(getIndex());
                    System.out.println("Ajout au panier : " + article.getArticleNom());
                    // TODO : Ajouter le panier à la session ou à la BDD
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
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
}
