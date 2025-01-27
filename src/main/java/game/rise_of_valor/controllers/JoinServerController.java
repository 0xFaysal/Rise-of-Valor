package game.rise_of_valor.controllers;

import game.rise_of_valor.models.Message;
import game.rise_of_valor.network.client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

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
    private void backBtn(ActionEvent event) throws IOException {
        mainPane.getChildren().remove(rootPane);
        FXMLLoader serverConnectionFxml = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/server-connect.fxml"));
        Parent root = serverConnectionFxml.load();
        ServerConnectController controller = serverConnectionFxml.getController();


        // Add the server connection content on top of the existing children
        mainPane.getChildren().add(root);
        controller.setMainPane(mainPane);
        controller.setLoadingController(loadingController);

        // Center the content in the mainPane
        root.setLayoutX((mainPane.getWidth() - root.prefWidth(-1)) / 2);
        root.setLayoutY((mainPane.getHeight() - root.prefHeight(-1)) / 2);
    }

    @FXML
   synchronized void  joinServer(ActionEvent event) {
        try {
            client = new Client(ipAddress.getText(), Integer.parseInt(port.getText()), this);
            client.start();

            Message message = new Message("connection",false);
            client.sendMessage(message);

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