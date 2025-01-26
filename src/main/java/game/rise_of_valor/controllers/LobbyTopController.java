package game.rise_of_valor.controllers;

import game.rise_of_valor.utils.CustomFont;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static game.rise_of_valor.controllers.LoadingController.userData;

public class LobbyTopController  implements Initializable{


    @FXML
    private Button playBtn;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MenuButton menuButton;

    private ContextMenu contextMenu;

   private String selectedMode;

    @FXML
    private Label level;

    @FXML
    private ImageView profileImage;



    @FXML
    private Label coinCount;

    @FXML
    private Label playerName;


    @FXML
    private Label userName;


    public void playNowBtnClick(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Stop the lobby loop
            LobbyViewController lobbyViewController = (LobbyViewController) stage.getScene().getUserData();
            if (lobbyViewController != null) {
                lobbyViewController.stopLobbyLoop();
            }

            if(menuButton.getText().equals("CLASSIC")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/game-play.fxml"));
                stage.getScene().setRoot(loader.load());
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/mode-selection.fxml"));
                stage.getScene().setRoot(loader.load());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       Font PressStart2P = Font.loadFont(getClass().getResourceAsStream("/game/rise_of_valor/assets/fonts/PressStart2P.ttf"), 24);
       setUserData();

       coinCount.setText(""+userData.getCoins());

        playBtn.setFont(PressStart2P);
        playBtn.setFont(javafx.scene.text.Font.font(PressStart2P.getFamily(), 14));



        contextMenu = new ContextMenu();

        contextMenu.getStyleClass().add("context-menu"); // Add this line

        MenuItem action1 = new MenuItem("MULTIPLAYER");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem action2 = new MenuItem("CLASSIC");
        contextMenu.getItems().addAll(action1, separator, action2);

        action1.setOnAction(event -> {
            menuButton.setText(action1.getText());
            menuButton.setGraphic(null); // Remove the icon
            menuButton.setStyle("-fx-background-color: #16f5fd; -fx-text-fill: #000; -fx-font-size: 14px; -fx-font-family:'Book Antiqua'; -fx-text-alignment: center; " );
        });
        action2.setOnAction(event -> {
            menuButton.setText(action2.getText());
            menuButton.setGraphic(null); // Remove the icon
            menuButton.setStyle("-fx-background-color: #228c22; -fx-text-fill: #000; -fx-font-size: 14px; -fx-font-family:'Book Antiqua'; -fx-text-alignment: center; " );
        });

        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.show(menuButton, 1051, 430);
            contextMenu.heightProperty().addListener((observable, oldValue, newValue) -> {
                contextMenu.setY(menuButton.getLayoutY() - newValue.doubleValue());
            });
        });

    }

    @FXML
    void shopBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/shop-view.fxml"));
        stage.getScene().setRoot(loader.load());
    }

    @FXML
    void vaultBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        System.out.println("Vault button clicked");
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/game-play.fxml"));
//        stage.getScene().setRoot(loader.load());
    }



    public void setUserData(){
        this.playerName.setText(userData.getName());
        this.userName.setText("@"+userData.getUserName());
        this.level.setText(""+userData.getLevel());
        this.profileImage.setImage(userData.getProfilePic());
    }
}
