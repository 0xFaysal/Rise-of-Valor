package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class login_registrationController implements Initializable {

    @FXML
    private Pane containerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loginPaneLoader = new FXMLLoader(Main.class.getResource("fxml/login-view.fxml"));
            Pane loginPane = loginPaneLoader.load();
            LoginController loginCtrl = loginPaneLoader.getController();
            loginCtrl.setContainerPane(containerPane);
            containerPane.getChildren().add(loginPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

