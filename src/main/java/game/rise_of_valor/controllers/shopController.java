package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
}

