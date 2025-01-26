package game.rise_of_valor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static game.rise_of_valor.controllers.LoadingController.userData;

public class winBgController implements Initializable {
    @FXML
    private Label coinNum;


    @FXML
    private Label killNum;

    @FXML
    void collectBtn(ActionEvent event) throws IOException {

        Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-view.fxml"));
        Pane lobbyView = loader.load();
        stage.getScene().setRoot(lobbyView);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coinNum.setText(""+userData.getCoin());
    }

    public void setKillNum(int killNum) {
        this.killNum.setText(""+killNum);
    }
}
