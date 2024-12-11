package game.rise_of_valor.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class IntroController implements Initializable {
    @FXML
    private ImageView imageView;

    @FXML
    private Pane mainPane; // The frame for your game content

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // First Fade Transition for Team Logo

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), imageView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), imageView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        SequentialTransition sequentialTransition = new SequentialTransition(fadeIn, pause, fadeOut);
        sequentialTransition.setCycleCount(1);
        sequentialTransition.setOnFinished(event -> {
            //set loading fxml in stage
            Stage stage = (Stage) mainPane.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/loading.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sequentialTransition.play();


    }
}










//            // Switch to Game Logo
//            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/game/rise_of_valor/assets/image/gameLogo.jpeg")).toExternalForm()));
//
//            // Fade in Game Logo
//            FadeTransition nextFadeIn = new FadeTransition(Duration.seconds(5), imageView);
//            nextFadeIn.setFromValue(0);
//            nextFadeIn.setToValue(1);
//            nextFadeIn.setOnFinished(e -> {
//                // Add ProgressBar inside the Pane
//                ProgressBar progressBar = new ProgressBar(0);
//                progressBar.setPrefWidth(mainPane.getPrefWidth() * 0.5); // Half the width of the frame
//                progressBar.setStyle("-fx-accent: #ad0202; -fx-control-inner-background: #2B2B2B;");
//
//                // Position ProgressBar above the logo
//                progressBar.setLayoutX((mainPane.getPrefWidth() - progressBar.getPrefWidth()) / 2); // Center horizontally
//                progressBar.setLayoutY(imageView.getFitHeight() - 30); // Slightly above the bottom of the image
//
//                mainPane.getChildren().add(progressBar); // Add ProgressBar to the main pane
//
//                // Animate ProgressBar
//                Timeline timeline = new Timeline(
//                        new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
//                        new KeyFrame(Duration.seconds(3), new KeyValue(progressBar.progressProperty(), 1))
//                );
//                timeline.setCycleCount(1); // Ensure it fills only once
//                timeline.play();
//            });
//            nextFadeIn.play();
//        });
//        sequentialTransition.play();



