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

//    @FXML
//    private Button googleSignUpBtn;

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
        }



        userData.setProfilePicName("pic1.png");
        userData.setData(usernameText, nameText, passwordText, 1, 0, DataManager.getProfilePic(userData.getProfilePicName()), false, isAgree);
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

//    @FXML
//    void goSignIn(MouseEvent event) {
//        if (containerPane == null) {
//            System.err.println("Error: containerPane is not initialized.");
//            return;
//        }
//
//        try {
//            // Swap back the positions
//            HBox parentHBox = (HBox) containerPane.getParent();
//            if (parentHBox == null) {
//                System.err.println("Error: parent HBox is null.");
//                return;
//            }
//
//            // Get the image view
//            ImageView imageView = (ImageView) parentHBox.getChildren().get(0);
//
//            // Remove both nodes and re-add in reverse order
//            parentHBox.getChildren().removeAll(imageView, containerPane);
//            parentHBox.getChildren().addAll(imageView, containerPane);
//
//            // Load the login view
//            containerPane.getChildren().clear();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/login-view.fxml"));
//            Pane loginPane = loader.load();
//            LoginController loginCtrl = loader.getController();
//            loginCtrl.setContainerPane(containerPane);
//            containerPane.getChildren().add(loginPane);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }




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
