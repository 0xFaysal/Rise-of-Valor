package game.rise_of_valor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LobbyViewController {

    @FXML
    private BorderPane rootPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Canvas canvas;

    private GraphicsContext gc;

    @FXML
    public void initialize() {

        Image image = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/lobby-bg.jpeg"));

        gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillText("Lobby View", 100, 100);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-top-view.fxml"));
            AnchorPane topView = loader.load();
            stackPane.getChildren().add(topView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}