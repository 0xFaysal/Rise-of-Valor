package game.rise_of_valor.controllers;

import game.rise_of_valor.models.Sprite;
import game.rise_of_valor.utils.LoadSprite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class LobbyViewController {

    @FXML
    private StackPane rootPane;
    @FXML
    private Canvas canvas;

    private GraphicsContext gc;


    private List<Image> playerSprite;

    @FXML
    public void initialize() {

        Image image = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/lobby-bg.jpeg"));
        playerSprite = LoadSprite.getPlayerIdle();
        System.out.println(playerSprite.size());
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(javafx.scene.paint.Color.RED);
//        gc.drawImage(playerSprite.get(0), 100, 100);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-top-view.fxml"));
            AnchorPane topView = loader.load();
            rootPane.getChildren().add(topView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}