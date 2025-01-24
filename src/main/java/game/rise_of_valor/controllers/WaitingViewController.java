package game.rise_of_valor.controllers;

import game.rise_of_valor.models.ClientData;
import game.rise_of_valor.network.client.Client;
import game.rise_of_valor.shareData.DataManager;
import game.rise_of_valor.shareData.UserData;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static game.rise_of_valor.shareData.DataManager.client;

public class WaitingViewController implements Initializable {

    @FXML
    private Pane animationPane;

    @FXML
    private HBox playerAddHBox;

    @FXML
    private ImageView searchIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animateSearchIcon();
        ClientData clientData = new ClientData(UserData.getUserName(), UserData.getLevel(), UserData.getProfilePicName());
        client.createDueModeRoom(clientData);
    }

    private void animateSearchIcon() {
        double paneWidth = animationPane.getPrefWidth();
        double paneHeight = animationPane.getPrefHeight();
        double startX = 0;
        double startY = 0;
        double endX = startX + paneWidth;
        double endY = startY + paneHeight;
        double centerX = startX + paneWidth / 2;
        double centerY = startY + paneHeight / 2;


        Path path = new Path();
        path.getElements().add(new MoveTo(startX, centerY));
        path.getElements().add(new CubicCurveTo(startX + paneWidth / 4, startY, centerX - paneWidth / 4, startY, centerX, centerY));
        path.getElements().add(new CubicCurveTo(centerX + paneWidth / 4, endY, endX - paneWidth / 4, endY, endX, centerY));
        path.getElements().add(new CubicCurveTo(endX - paneWidth / 4, startY, centerX + paneWidth / 4, startY, centerX, centerY));
        path.getElements().add(new CubicCurveTo(centerX - paneWidth / 4, endY, startX + paneWidth / 4, endY, startX, centerY));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(20)); // Increase duration for smoother animation
        pathTransition.setPath(path);
        pathTransition.setNode(searchIcon);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }

    public void addPlayer(String username, String profilePic, String level) {
        VBox playerAddVBox = new VBox();
        playerAddVBox.setSpacing(10);
        playerAddVBox.setAlignment(javafx.geometry.Pos.CENTER);
        ImageView playerIcon = new ImageView(DataManager.getProfilePic(profilePic));
        playerIcon.setFitWidth(50);
        playerIcon.setFitHeight(50);
        Label playerLabel = new Label(username);
        playerLabel.setStyle("-fx-text-fill: #ffffff");
        Label levelLabel = new Label(level);
        levelLabel.setStyle("-fx-text-fill: #ffffff");
        playerAddVBox.getChildren().addAll(playerIcon, playerLabel, levelLabel);
        playerAddHBox.getChildren().add(playerAddVBox);
    }

    public void addPlayer(String username, Image profilePic, String level) {
        VBox playerAddVBox = new VBox();
        playerAddVBox.setSpacing(10);
        playerAddVBox.setAlignment(javafx.geometry.Pos.CENTER);
        ImageView playerIcon = new ImageView(profilePic);
        playerIcon.setFitWidth(50);
        playerIcon.setFitHeight(50);
        Label playerLabel = new Label(username);
        playerLabel.setStyle("-fx-text-fill: #ffffff");
        Label levelLabel = new Label(level);
        levelLabel.setStyle("-fx-text-fill: #ffffff");
        playerAddVBox.getChildren().addAll(playerIcon, playerLabel, levelLabel);
        playerAddHBox.getChildren().add(playerAddVBox);
    }
}