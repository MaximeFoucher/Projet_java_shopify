package Controleur;

import Dao.CommanderDAO;
import Dao.CommanderDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import Modele.Client;
import Modele.Commander;

import java.util.List;

public class PanierController {

    private Main mainApp;
    private Client client;
    private CommanderDAO commanderDAO;

    @FXML private Label nomLabel;
    @FXML private ListView<String> panierList; // si tu l’as déclaré dans le FXML

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        this.client = mainApp.getClientConnecte(); // Tu peux aussi faire ça ici si tu veux
        this.commanderDAO = new CommanderDAOImpl(mainApp.getDaoFactory());
    }

    @FXML
    public void handleValiderPanier(){
        // Logique de validation (à compléter)
    }

    @FXML
    public void handleViderPanier(){
        try {
            commanderDAO.viderPanier(client); // Appelle la méthode DAO
            panierList.getItems().clear();    // Réinitialise la liste visible
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRetour(){
        try {
            mainApp.switchToPageAccueil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
