package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class shopController implements Initializable {

    @FXML
    private AnchorPane shopMainAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader shopInsideLoader = new FXMLLoader(Main.class.getResource("fxml/shopInside-view.fxml"));
            AnchorPane shopInsidePane = shopInsideLoader.load();
            shopInsideController shopInsideCtrl = shopInsideLoader.getController();
            shopInsideCtrl.setShopInsideAnchorPane(shopMainAnchorPane);
            shopMainAnchorPane.getChildren().add(shopInsidePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-view.fxml"));
        stage.getScene().setRoot(loader.load());
    }
}

