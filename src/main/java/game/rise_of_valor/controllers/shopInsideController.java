package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class shopInsideController {
    protected AnchorPane ShopInsideAnchorPane;

    public void setShopInsideAnchorPane(AnchorPane shopMainAnchorPane) {
        this.ShopInsideAnchorPane = shopMainAnchorPane;
    }

    @FXML
    private void nextItems() {
        System.out.println("Next items");
        try {
            FXMLLoader shopInside2Loader = new FXMLLoader(Main.class.getResource("fxml/shopInside2-view.fxml"));
            AnchorPane shopInside2AnchorPane = shopInside2Loader.load();

            shopInside2Controller shopInside2Ctrl = shopInside2Loader.getController();
            shopInside2Ctrl.setShopInside2AnchorPane(ShopInsideAnchorPane);

            ShopInsideAnchorPane.getChildren().clear();
            ShopInsideAnchorPane.getChildren().add(shopInside2AnchorPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void buyPlayer1(ActionEvent event) {

    }

    @FXML
    void buyPlayer2(ActionEvent event) {

    }

    @FXML
    void buyPlayer4(ActionEvent event) {

    }

    @FXML
    private void previousItems() {
        System.out.println("No previous items");
//        try {
//            FXMLLoader shopInside2Loader = new FXMLLoader(Main.class.getResource("fxml/shopInside2-view.fxml"));
//            AnchorPane shopInside2AnchorPane = shopInside2Loader.load();
//
//            shopInside2Controller shopInside2Ctrl = shopInside2Loader.getController();
//            shopInside2Ctrl.setShopInside2AnchorPane(ShopInsideAnchorPane);
//
//            ShopInsideAnchorPane.getChildren().clear();
//            ShopInsideAnchorPane.getChildren().add(shopInside2AnchorPane);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
