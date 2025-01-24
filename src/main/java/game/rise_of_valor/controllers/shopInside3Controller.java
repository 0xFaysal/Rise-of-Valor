package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class shopInside3Controller {
    private AnchorPane ShopInside3AnchorPane;

    public void setShopInside3AnchorPane(AnchorPane shopInside2AnchorPane) {this.ShopInside3AnchorPane = shopInside2AnchorPane;}

    @FXML
    private void nextItems() {
        System.out.println("Next items");
    }
    @FXML
    private void previousItems() {
        System.out.println("Previous items");
    }


}
