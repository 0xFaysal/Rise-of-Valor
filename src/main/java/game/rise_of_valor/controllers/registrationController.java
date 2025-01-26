package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import game.rise_of_valor.shareData.DataManager;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;

import java.io.IOException;

import static game.rise_of_valor.controllers.LoadingController.dataManager;
import static game.rise_of_valor.controllers.LoadingController.userData;


public class registrationController {

    private Pane containerPane;

    @FXML
    private CheckBox IAgree;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;


    @FXML
    private TextField username;

    public void setContainerPane(Pane containerPane) {
        this.containerPane = containerPane;
    }


    @FXML
    void signUp(ActionEvent event) {
        String nameText = name.getText();
        String passwordText = password.getText();
        String usernameText = username.getText();
        boolean isAgree = IAgree.isSelected();

        if(nameText.isEmpty() || passwordText.isEmpty() || usernameText.isEmpty() || !isAgree) {
            System.out.println("Please fill all the fields");
            return;
        }else {
            System.out.println("Name: " + nameText);
            System.out.println("Password: " + passwordText);
            System.out.println("Username: " + usernameText);
        }

        userData.setProfilePicName("pic1.png");
        userData.setData(usernameText, nameText, passwordText, 1, 200, DataManager.getProfilePic(userData.getProfilePicName()), false, isAgree);
        System.out.println("Username: " + userData);


        // Load the lobby view
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
    private void goSignIn() {
        try {
            // Load the registration view
            FXMLLoader signInPaneLoader = new FXMLLoader(Main.class.getResource("/game/rise_of_valor/fxml/login-view.fxml"));
            Pane signInPane = signInPaneLoader.load();

            LoginController loginCtrl = signInPaneLoader.getController();
            loginCtrl.setContainerPane(containerPane);

            // Reference the parent HBox
            HBox parentHBox = (HBox) containerPane.getParent();

            // Get the current children of the HBox
            Node imageViewNode = parentHBox.getChildren().get(1); // ImageView
            Node containerNode = parentHBox.getChildren().get(0); // ContainerPane

            // Swap their positions
            parentHBox.getChildren().clear(); // Clear existing children
            parentHBox.getChildren().addAll(imageViewNode, containerNode); // Add in swapped order

            // Set the containerPane to display the registration view
            containerPane.getChildren().clear(); // Clear previous content
            containerPane.getChildren().add(signInPane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
