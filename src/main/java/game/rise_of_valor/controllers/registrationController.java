package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import game.rise_of_valor.models.ClientData;
import game.rise_of_valor.models.Message;
import game.rise_of_valor.shareData.DataManager;
import game.rise_of_valor.shareData.localCustomData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static game.rise_of_valor.controllers.LoadingController.dataManager;
import static game.rise_of_valor.controllers.LoadingController.userData;
import static game.rise_of_valor.shareData.DataManager.client;
import static game.rise_of_valor.shareData.localCustomData.profileImages;
import static game.rise_of_valor.storage.LocalStorageManager.signupData;


public class registrationController {


    @FXML
    private Label warningMsg;

    private Pane containerPane;

    @FXML
    private CheckBox IAgree;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password1;

    @FXML
    private TextField username;

    public void setContainerPane(Pane containerPane) {
        this.containerPane = containerPane;
    }


    @FXML
    void signUp(ActionEvent event) {
        String nameText = name.getText();
        String passwordText = password.getText();
        String confirmPass = password1.getText();
        String usernameText = username.getText();
        boolean isAgree = IAgree.isSelected();


        if(nameText.isEmpty() || passwordText.isEmpty()|| confirmPass.isEmpty() || usernameText.isEmpty() || !isAgree || !passwordText.equals(confirmPass)) {
            System.out.println("Please fill all the fields");
            warningMsg.setText("Please fill all the fields");
            return;
        }else {
            System.out.println("Name: " + nameText);
            System.out.println("Password: " + passwordText);
            System.out.println("Username: " + usernameText);
        }

        userData.setData(usernameText, nameText, passwordText, 1, 0, 0, false, isAgree);

        ClientData clientData = new ClientData(usernameText, nameText, passwordText, 1, profileImages.get(1).getImageLink(), 0, userData);

        Message message = new Message("createAccount", clientData);

        client.sendMessage(message);
        signupData(nameText, passwordText);




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
