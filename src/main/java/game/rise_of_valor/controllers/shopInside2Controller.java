package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class shopInside2Controller {
    private AnchorPane ShopInside2AnchorPane;

    public void setShopInside2AnchorPane(AnchorPane shopInsideAnchorPane) {
        this.ShopInside2AnchorPane = shopInsideAnchorPane;
    }

    @FXML
    private void nextItems() {
        System.out.println("No next items");
//        try {
//            FXMLLoader shopInsideLoader = new FXMLLoader(Main.class.getResource("fxml/shopInside-view.fxml"));
//            AnchorPane shopInsideAnchorPane = shopInsideLoader.load();
//
//            shopInsideController shopInsideCtrl = shopInsideLoader.getController();
//            shopInsideCtrl.setShopInsideAnchorPane(ShopInside2AnchorPane);
//
//            ShopInside2AnchorPane.getChildren().clear();
//            ShopInside2AnchorPane.getChildren().add(shopInsideAnchorPane);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    private void previousItems() {
        System.out.println("Previous items");
        try {
            FXMLLoader shopInsideLoader = new FXMLLoader(Main.class.getResource("fxml/shopInside-view.fxml"));
            AnchorPane shopInsideAnchorPane = shopInsideLoader.load();

            shopInsideController shopInsideCtrl = shopInsideLoader.getController();
            shopInsideCtrl.setShopInsideAnchorPane(ShopInside2AnchorPane);

            ShopInside2AnchorPane.getChildren().clear();
            ShopInside2AnchorPane.getChildren().add(shopInsideAnchorPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
