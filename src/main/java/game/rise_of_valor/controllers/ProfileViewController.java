package game.rise_of_valor.controllers;

import game.rise_of_valor.models.ProfileImage;
import game.rise_of_valor.shareData.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import static game.rise_of_valor.shareData.localCustomData.profileImage;
import static game.rise_of_valor.shareData.localCustomData.profileImages;

public class ProfileViewController implements Initializable {
    @FXML
    private TextField name;

    @FXML
    private Label coinCount;

    @FXML
    private HBox profiles;


    @FXML
    private ImageView profilePic;


    @FXML
    private Label userName;

    @FXML
    void updateBtn(ActionEvent event) {
        UserData.setName(name.getText());
    }

    @FXML
    void backBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-view.fxml"));
        stage.getScene().setRoot(loader.load());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coinCount.setText(String.valueOf(UserData.getCoin()));
        userName.setText(UserData.getUserName());
//        profilePic.setImage(UserData.getProfilePic());
        name.setText(UserData.getName());
        profileImages.forEach(profileImage -> {
            viewAllProfile(profileImage.getImageLink(), profileImage.isPurchased(), profileImage.isSet(), profileImage.getPrice(),profileImage);
        });
    }

    private void viewAllProfile(String image, boolean isBuy, boolean isSet, int price, ProfileImage profileImage) {
        {
            VBox profile = new VBox();
            profile.setAlignment(Pos.TOP_CENTER);
            profile.setSpacing(10);

            ImageView imageView = new ImageView();
            imageView.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(image)));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
            profile.getChildren().add(imageView);

            Label priceLabel = new Label();

            if (!isBuy) {
                priceLabel.setText("Price: "+price);
            } else {
                priceLabel.setText("Owned");
            }
            priceLabel.setFont(new javafx.scene.text.Font("Arial", 15));
            priceLabel.setStyle("-fx-text-fill: #fde588; -fx-font-weight: bold ; -fx-background-color: #01191d; -fx-background-radius: 10px;-fx-padding: 5px");
            profile.getChildren().add(priceLabel);


            if (!isSet) {
                Button button = new Button();
                if (isBuy) {
                    button.setText("Set");
                    button.setFont(new javafx.scene.text.Font("Arial", 15));
                    button.setStyle("-fx-background-color: #000957; -fx-text-fill: #ffffff");
                    button.setOnAction(event -> {
                        //set profile
                        UserData.setProfilePicName(image);
                        profilePic.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(image)));
                        profileImage.setSet(true);
                    });
                } else {
                    button.setText("Buy");
                    button.setFont(new javafx.scene.text.Font("Arial", 15));
                    button.setStyle("-fx-background-color: #2E5077; -fx-text-fill: #F6F4F0");
                    button.setOnAction(event -> {
                        //buy profile
                        if (UserData.getCoin() >= price) {
                            UserData.setCoin(UserData.getCoin() - price);
                            coinCount.setText(String.valueOf(UserData.getCoin()));
                            priceLabel.setText("Owned");
                            button.setText("Set");
                            button.setStyle("-fx-background-color: #000957; -fx-text-fill: #ffffff");
                            profileImage.setPurchased(true);

                            button.setOnAction(event1 -> {
                                //set profile
                                UserData.setProfilePicName(image);
//                                UserData.setProfilePic(new javafx.scene.image.Image(getClass().getResourceAsStream(image)));
                                profilePic.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(image)));
                            });
                        }
                    });
                }

                profile.getChildren().add(button);
            }
            profiles.getChildren().add(profile);
        }
    }
}