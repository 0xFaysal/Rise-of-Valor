package game.rise_of_valor.server_controllers;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private ProgressBar progressbar;

    private boolean dataManagerInitialized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a new Timeline object
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressbar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(progressbar.progressProperty(), 1))
        );
        timeline.setCycleCount(1); // Ensure it fills only once
        timeline.play();

        // Add a listener to the progress property
        progressbar.progressProperty().addListener((observable, oldValue, newValue) -> {
            double roundedValue = BigDecimal.valueOf(newValue.doubleValue())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
//            System.out.println(roundedValue);

            if (roundedValue == 0.3) {
                System.out.println("Internet connection status: " + InternetConnectionChecker.isInternetAvailable());
            }

            if (newValue.doubleValue() == 1.0) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/server_fxml/start-server-view.fxml"));
                    mainPane.getChildren().clear();
                    mainPane.getChildren().add(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static class InternetConnectionChecker {
        public static boolean isInternetAvailable() {
            try {
                InetAddress address = InetAddress.getByName("google.com");
                return address.isReachable(2000); // Timeout in milliseconds
            } catch (IOException e) {
                return false;
            }
        }
    }
}