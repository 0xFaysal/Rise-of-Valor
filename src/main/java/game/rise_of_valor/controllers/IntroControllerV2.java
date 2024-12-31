package game.rise_of_valor.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IntroControllerV2 implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private BorderPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load the video
        String videoPath = getClass().getResource("/game/rise_of_valor/assets/image/InceptionCoderLogo.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        // Fade in the video
        FadeTransition fadeInVideo = new FadeTransition(Duration.seconds(1), mediaView);
        fadeInVideo.setFromValue(0);
        fadeInVideo.setToValue(1);

        // Fade out the video
        FadeTransition fadeOutVideo = new FadeTransition(Duration.seconds(3), mediaView);
        fadeOutVideo.setFromValue(1);
        fadeOutVideo.setToValue(0);
        fadeOutVideo.setOnFinished(event -> showGameLogo());

        // Play the video and apply fade transitions
        mediaPlayer.setOnReady(() -> {
            fadeInVideo.play();
            fadeInVideo.setOnFinished(e -> {
                mediaPlayer.play();
                mediaPlayer.setOnEndOfMedia(() -> fadeOutVideo.play());
            });
        });
    }

    private void showGameLogo() {
        // Create an ImageView for the game logo
        ImageView imageView = new ImageView(new Image(getClass().getResource("/game/rise_of_valor/assets/image/gameLogo.jpeg").toExternalForm()));
        imageView.setFitHeight(544);
        imageView.setFitWidth(960);
        imageView.setPreserveRatio(true);

        // Add the ImageView to the BorderPane
        rootPane.setCenter(imageView);

        // Apply a fade-in effect to the game logo
        FadeTransition fadeInLogo = new FadeTransition(Duration.seconds(3), imageView);
        fadeInLogo.setFromValue(0);
        fadeInLogo.setToValue(1);
        fadeInLogo.setOnFinished(event -> switchToNextScene());
        fadeInLogo.play();
    }

    private void switchToNextScene() {
        // Transition to the next scene (e.g., loading screen or main game)
        Stage stage = (Stage) rootPane.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/loading.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}