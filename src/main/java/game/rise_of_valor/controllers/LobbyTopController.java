package game.rise_of_valor.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LobbyTopController  implements Initializable{

    int n=1;

    @FXML
    private Button playBtn;

    @FXML
    private AnchorPane rootPane;

    @FXML
    void counter(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        System.out.println("Counter"+n++);
        LobbyViewController lobbyViewController = (LobbyViewController) stage.getScene().getUserData();
        lobbyViewController.print();
    }

    public void playNowBtnClick(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Stop the lobby loop
            LobbyViewController lobbyViewController = (LobbyViewController) stage.getScene().getUserData();
            if (lobbyViewController != null) {
                lobbyViewController.stopLobbyLoop();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/game-play.fxml"));
            stage.getScene().setRoot(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
