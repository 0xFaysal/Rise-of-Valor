package game.rise_of_valor.controllers;

import game.rise_of_valor.network.client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import static game.rise_of_valor.shareData.DataManager.client;

public class JoinServerController implements Client.ConnectionListener {
    @FXML
    private TextField ipAddress;

    @FXML
    private TextField port;

    @FXML
    private AnchorPane rootPane;


    @FXML
    private Label tryagain;

    @FXML
    private Label wrongMsg;

    Pane mainPane;
    LoadingController loadingController;

    @FXML
    void closeBtn(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void joinServer(ActionEvent event) {
        try {
            client = new Client(ipAddress.getText(), Integer.parseInt(port.getText()), this);
            client.start();
        } catch (Exception e) {
            viewPopup();
            e.printStackTrace();
        }
    }

    public void closePopup() {
        mainPane.getChildren().remove(rootPane);
        if (loadingController != null) {
            loadingController.playDataManagerTimeline();
        }
    }

    public void viewPopup() {
        if (!mainPane.getChildren().contains(rootPane)) {
            mainPane.getChildren().add(rootPane);
        }
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public void setLoadingController(LoadingController loadingController) {
        this.loadingController = loadingController;
    }

    @Override
    public void onConnectionSuccessful() {
        Platform.runLater(() -> {
            System.out.println("Connected to server on " + ipAddress.getText() + ":" + port.getText());
            closePopup();
        });
    }

    @Override
    public void onConnectionFailed() {
        Platform.runLater(() -> {
            System.out.println("Failed to connect to server on " + ipAddress.getText() + ":" + port.getText());
            wrongMsg.setText("Wrong IP Address or Port");
            tryagain.setText("Try Again");
            viewPopup();
        });
    }
}