package game.rise_of_valor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class loginController {

    private Pane containerPane;

    @FXML
    private Button GoogleSignInBtn;

    @FXML
    private Button SignInBtn;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private CheckBox rememberMe;

    public void setContainerPane(Pane containerPane) {
        this.containerPane = containerPane;
    }

    @FXML
    void goRegister(MouseEvent event) {
        try {
            containerPane.getChildren().clear();
            FXMLLoader registerPaneLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/registration-view.fxml"));
            Pane registerPane = registerPaneLoader.load();
            registrationController registerCtrl = registerPaneLoader.getController();
            registerCtrl.setContainerPane(containerPane);
            containerPane.getChildren().add(registerPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
