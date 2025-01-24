package game.rise_of_valor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;


public class ModeSelectionController {
    @FXML
    public void duoModeClicked(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/waiting-view.fxml"));
        stage.getScene().setRoot(loader.load());
    }
    @FXML
    public void quadModeClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Game started in quad mode");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/waiting-view.fxml"));
        stage.getScene().setRoot(loader.load());
    }
}
