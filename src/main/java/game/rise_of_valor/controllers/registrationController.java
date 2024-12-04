package game.rise_of_valor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class registrationController {

    private Pane containerPane;

    @FXML
    private CheckBox IAgree;

    @FXML
    private TextField email;

    @FXML
    private Button googleSignUpBtn;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField username;

    public void setContainerPane(Pane containerPane) {
        this.containerPane = containerPane;
    }

    @FXML
    void goSignIn(MouseEvent event) {
        try {
            containerPane.getChildren().clear();
            FXMLLoader loginPaneLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/login-view.fxml"));
            Pane loginPane = loginPaneLoader.load();
            loginController loginCtrl = loginPaneLoader.getController();
            loginCtrl.setContainerPane(containerPane);
            containerPane.getChildren().add(loginPane);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
