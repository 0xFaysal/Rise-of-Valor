package game.rise_of_valor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerConnectController {

    @FXML
    private AnchorPane rootPane;

    private Pane mainPane;
    private LoadingController loadingController;


    @FXML
    void closeBtn(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void joinServer(ActionEvent event) {
        try {
            FXMLLoader joinServerFxml = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/join-server.fxml"));
            Pane joinServerPane = joinServerFxml.load();
            JoinServerController controller = joinServerFxml.getController();
            controller.setMainPane(mainPane);
            controller.setLoadingController(loadingController);

            mainPane.getChildren().clear();
            mainPane.getChildren().add(joinServerPane);

            joinServerPane.setLayoutX((mainPane.getWidth() - joinServerPane.prefWidth(-1)) / 2);
            joinServerPane.setLayoutY((mainPane.getHeight() - joinServerPane.prefHeight(-1)) / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void startServerBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/server_fxml/loading.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Rise of valor Server");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the server Connection PopUp window
            closePopup();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public void setLoadingController(LoadingController loadingController) {
        this.loadingController = loadingController;
    }


    public void closePopup() {
        mainPane.getChildren().remove(rootPane);
        if (loadingController != null) {
            loadingController.playDataManagerTimeline();
        }
    }



}
