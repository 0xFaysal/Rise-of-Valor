package game.rise_of_valor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyTopController {

    int n=1;

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
}
