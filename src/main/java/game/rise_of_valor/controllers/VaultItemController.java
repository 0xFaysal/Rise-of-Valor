package game.rise_of_valor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class VaultItemController {
    @FXML
    private ImageView image;

    @FXML
    void equipBtn(ActionEvent event) {

    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
