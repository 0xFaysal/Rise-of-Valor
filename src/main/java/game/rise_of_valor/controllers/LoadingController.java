package game.rise_of_valor.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {


    @FXML
    private Pane mainPane;

    @FXML
    private ProgressBar progressbar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Timeline timeline = new Timeline(

                        new KeyFrame(Duration.ZERO, new KeyValue(progressbar.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(10), new KeyValue(progressbar.progressProperty(), 1))
                );
                timeline.setCycleCount(1); // Ensure it fills only once
                timeline.play();




        // Add a listener to the progress property
        progressbar.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0) {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/login-registration.fxml"));
                    mainPane.getChildren().clear();
                    mainPane.getChildren().add(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
