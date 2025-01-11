package game.rise_of_valor.controllers;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
        if (containerPane == null) {
            System.err.println("Error: containerPane is not initialized.");
            return;
        }

        try {
            // Swap back the positions
            HBox parentHBox = (HBox) containerPane.getParent();
            if (parentHBox == null) {
                System.err.println("Error: parent HBox is null.");
                return;
            }

            // Get the image view
            ImageView imageView = (ImageView) parentHBox.getChildren().get(0);

            // Remove both nodes and re-add in reverse order
            parentHBox.getChildren().removeAll(imageView, containerPane);
            parentHBox.getChildren().addAll(imageView, containerPane);

            // Load the login view
            containerPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/login-view.fxml"));
            Pane loginPane = loader.load();
            LoginController loginCtrl = loader.getController();
            loginCtrl.setContainerPane(containerPane);
            containerPane.getChildren().add(loginPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




//    @FXML
//    void goSignIn(MouseEvent event) {
//        try {
//            containerPane.getChildren().clear();
//            FXMLLoader loginPaneLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/login-view.fxml"));
//            Pane loginPane = loginPaneLoader.load();
//            LoginController loginCtrl = loginPaneLoader.getController();
//            loginCtrl.setContainerPane(containerPane);
//            containerPane.getChildren().add(loginPane);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
