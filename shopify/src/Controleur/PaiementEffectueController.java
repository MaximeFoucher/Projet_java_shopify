package Controleur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class PaiementEffectueController {

    @FXML private ProgressIndicator loader;
    @FXML private Label messageLabel;
    @FXML private Button btnRetourAccueil;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Simule un chargement de 2 secondes
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simule le paiement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                loader.setVisible(false);
                messageLabel.setText("✅ Paiement effectué avec succès !");
                btnRetourAccueil.setVisible(true);
            });
        }).start();
    }

    @FXML
    public void handleRetourAccueil() {
        try {
            mainApp.switchToPageAccueil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
