package Controleur;

import Dao.CommanderDAO;
import Dao.CommanderDAOImpl;
import Modele.Article;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import Modele.Client;
import Modele.Commander;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.List;

public class PanierController {
    @FXML
    private ListView<String> articlesList;

    @FXML private TableView<Article> panierTable;
    @FXML private TableColumn<Article, String> colNomArticle;
    @FXML private TableColumn<Article, String> colMarqueArticle;
    @FXML private TableColumn<Article, Double> colPrixUniteArticle;
    @FXML private TableColumn<Article, Integer> colQuantiteArticle;

    @FXML private Label prixTotalBrutLabel;
    @FXML private Label reductionLabel;
    @FXML private Label prixTotalLabel;




    private Main mainApp;
    private Client client;
    private CommanderDAO commanderDAO;
    private Commander commander;
    private List<Article> articles;

    @FXML private Label nomLabel;
    @FXML private ListView<String> panierList; // si tu l’as déclaré dans le FXML

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        this.client = mainApp.getClientConnecte(); // Tu peux aussi faire ça ici si tu veux
        this.commanderDAO = new CommanderDAOImpl(mainApp.getDaoFactory());

    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleSupprimerArticle(){
        Article selected = panierTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            commanderDAO.supprimerArticledeCommande(commanderDAO.getPanierActif(client), selected);
            chargerPanier();
        }
    }


    @FXML
    public void handleValiderPanier() {
        try {
            commanderDAO.reglerPanier(this.commanderDAO.getPanierActif(client), client);
            mainApp.showConfirmationPaiement(); // Affiche l'écran de confirmation
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleViderPanier(){
        try {
            commanderDAO.viderPanier(client); // Appelle la méthode DAO
            articlesList.getItems().clear();    // Réinitialise la liste visible
            chargerPanier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afficherArticles() {
        ObservableList<Article> observableArticles = FXCollections.observableArrayList(articles);
        panierTable.setItems(observableArticles);
    }


    private void initColonnes() {
        colNomArticle.setCellValueFactory(new PropertyValueFactory<>("articleNom"));
        colMarqueArticle.setCellValueFactory(new PropertyValueFactory<>("articleMarque"));
        colPrixUniteArticle.setCellValueFactory(new PropertyValueFactory<>("articlePrixUnite"));

        // Pour la quantité, on n’a pas de propriété dans Article → il faut passer par un objet wrapper ou colonne custom :
        colQuantiteArticle.setCellValueFactory(cellData -> {
            Article article = cellData.getValue();
            int qte = commanderDAO.getQuantiteArticleFromCommande(commanderDAO.getPanierActif(client), article);
            return new javafx.beans.property.SimpleIntegerProperty(qte).asObject();
        });
    }

    public void setPanier(List<Article> articles) {
        this.articles = articles;
        initColonnes();
        afficherArticles();
        chargerPanier();
    }

    @FXML
    public void handleRetour(){
        try {
            mainApp.switchToPageAccueil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerPanier() {
        Commander panier = commanderDAO.getPanierActif(client);
        List<Article> articlesPanier = commanderDAO.getArticlesCommande(panier);
        this.articles = articlesPanier;

        ObservableList<Article> observableArticles = FXCollections.observableArrayList(articlesPanier);
        panierTable.setItems(observableArticles);

        calculerPrixTotal(); // total à jour
    }

    private void calculerPrixTotal() {
        Commander panier = commanderDAO.getPanierActif(client);
        if (panier == null) return;

        // Prix total brut = somme des quantités × prix_unite
        double totalBrut = 0.0;
        for (Article article : articles) {
            int qte = commanderDAO.getQuantiteArticleFromCommande(panier, article);
            totalBrut += article.getArticlePrixUnite() * qte;
        }

        // Prix après réduction
        int totalReduit = commanderDAO.getNoteCommande(panier);
        double reduction = totalBrut - totalReduit;

        // Mise à jour des labels
        prixTotalBrutLabel.setText(String.format("Prix brut : %.2f €", totalBrut));
        reductionLabel.setText(String.format("Réduction : %.2f €", reduction));
        prixTotalLabel.setText(String.format("Prix total : %.2f €", (double) totalReduit));
    }



}
