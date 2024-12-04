package game.rise_of_valor.controllers;

import game.rise_of_valor.game_engine.GameLoop;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;

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
                    new GameLoop(canvas, newScene);
                }
            }
        });
    }
}