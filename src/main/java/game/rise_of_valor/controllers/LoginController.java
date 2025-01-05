package game.rise_of_valor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

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
    void SignInBtnClick(ActionEvent event) {
        // go to lobby
        try {
            Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-view.fxml"));
            Pane lobbyView = loader.load();
            stage.getScene().setRoot(lobbyView);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
