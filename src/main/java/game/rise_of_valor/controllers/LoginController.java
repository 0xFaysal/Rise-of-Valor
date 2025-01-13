package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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
    private void goRegister() {
        try {
            // Load the registration view
            FXMLLoader registrationPaneLoader = new FXMLLoader(Main.class.getResource("/game/rise_of_valor/fxml/registration-view.fxml"));
            Pane registrationPane = registrationPaneLoader.load();

            registrationController registerCtrl = registrationPaneLoader.getController();
            registerCtrl.setContainerPane(containerPane);

            // Reference the parent HBox
            HBox parentHBox = (HBox) containerPane.getParent();

            // Get the current children of the HBox
            Node imageViewNode = parentHBox.getChildren().get(0); // ImageView
            Node containerNode = parentHBox.getChildren().get(1); // ContainerPane

            // Swap their positions
            parentHBox.getChildren().clear(); // Clear existing children
            parentHBox.getChildren().addAll(containerNode, imageViewNode); // Add in swapped order

            // Set the containerPane to display the registration view
            containerPane.getChildren().clear(); // Clear previous content
            containerPane.getChildren().add(registrationPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @FXML
//    void goRegister(MouseEvent event) {
//        try {
//            containerPane.getChildren().clear();
//            FXMLLoader registerPaneLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/registration-view.fxml"));
//            Pane registerPane = registerPaneLoader.load();
//            registrationController registerCtrl = registerPaneLoader.getController();
//            registerCtrl.setContainerPane(containerPane);
//            containerPane.getChildren().add(registerPane);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
