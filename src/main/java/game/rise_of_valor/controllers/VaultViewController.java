package game.rise_of_valor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class VaultViewController implements Initializable {
    @FXML
    private HBox itemBox;

    @FXML
    void backBtn(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/vault-item.fxml"));
        VaultItemController vaultItemController = loader.getController();


    }
}
