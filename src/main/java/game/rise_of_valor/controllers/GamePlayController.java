package game.rise_of_valor.controllers;

import game.rise_of_valor.game_engine.GameLoop;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePlayController implements Initializable {

    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvas.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldScene, Scene newScene) {
                if (newScene != null) {

                    // Load the custom cursor image
                    Image cursorImage = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/cursor1.png"));
                    // Create a new ImageCursor with the loaded image
                    ImageCursor customCursor = new ImageCursor(cursorImage);
                    // Set the custom cursor for the scene
                    newScene.setCursor(customCursor);
                    new GameLoop(canvas, newScene);
//                    new GameLoopT(canvas, newScene).start();
                }
            }
        });
    }
}