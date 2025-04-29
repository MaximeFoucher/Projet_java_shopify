package Controleur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
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
        // Exemple provisoire
        System.out.println("Redirection vers l'interface Admin (à implémenter)");
        // Tu mettras ici le code pour charger AdminView.fxml quand tu l'auras
    }

    public void switchToClientView() throws Exception {
        // Exemple provisoire
        System.out.println("Redirection vers l'interface Client (à implémenter)");
        // Tu mettras ici le code pour charger ClientView.fxml quand tu l'auras
    }

    public static void main(String[] args) {
        launch(args);
    }
}
