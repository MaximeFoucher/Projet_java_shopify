package Controleur;

import Dao.CommanderDAO;
import Dao.CommanderDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import Modele.Client;
import javafx.scene.control.Label;
import Modele.Commander;

import java.util.List;

public class PanierController {
    private Main mainApp;
    private Client client;
    private CommanderDAO commanderDAO = new CommanderDAOImpl(mainApp.getDaoFactory());

    @FXML private Label nomLabel;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void setClient(Client client) { this.client = client; }


    @FXML
    public void handleValiderPanier(){

    }

    @FXML
    public void handleViderPanier(){
        try{
            commanderDAO.viderPanier(client);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    public void handleRetour(){
        try{
            mainApp.switchToPageAccueil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
