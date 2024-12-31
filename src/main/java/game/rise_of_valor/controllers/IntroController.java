package game.rise_of_valor.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IntroController implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private StackPane rootPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
<<<<<<< HEAD
        playMedia();
    }

    private void playMedia() {
        // Load the video
        String videoPath = getClass().getResource("/game/rise_of_valor/assets/image/InceptionCoderLogo.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        // Fade in the video
        FadeTransition fadeInVideo = new FadeTransition(Duration.seconds(0.5), mediaView);
        fadeInVideo.setFromValue(0);
        fadeInVideo.setToValue(1);

        // Fade out the video
        FadeTransition fadeOutVideo = new FadeTransition(Duration.seconds(3), mediaView);
        fadeOutVideo.setFromValue(1);
        fadeOutVideo.setToValue(0);
        fadeOutVideo.setOnFinished(event -> {
            mediaPlayer.dispose();
            switchToNextScene();
        });

        // Play the video and apply fade transitions
        mediaPlayer.setOnReady(() -> {
            fadeInVideo.play();
            fadeInVideo.setOnFinished(e -> {
                mediaPlayer.play();
                mediaPlayer.setOnEndOfMedia(() -> fadeOutVideo.play());
            });
        });
    }

    private void switchToNextScene() {

        // Transition to the next scene (e.g., loading screen or main game)

        try {
            rootPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/loading.fxml"));
            rootPane.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
=======

        // First Fade Transition for Team Logo
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), imageView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));

        // Second Fade Transition for Team Logo
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


>>>>>>> 614b72f (deleted code re written for the game logo scene, login-registration page image changed.)
    }


}