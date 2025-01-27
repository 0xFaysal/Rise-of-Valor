package game.rise_of_valor.controllers;

import game.rise_of_valor.models.ClientData;
import game.rise_of_valor.models.Message;
import game.rise_of_valor.shareData.UserData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static game.rise_of_valor.shareData.DataManager.client;

public class MessageViewController implements Initializable {

    @FXML
    private VBox messageBox;

    @FXML
    private TextField textField;

    @FXML
    void backBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-view.fxml"));
        stage.getScene().setRoot(loader.load());
    }


    @FXML
    void sendBtn(ActionEvent event) {
        String message = textField.getText();

        if(!message.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new javafx.geometry.Insets(5, 5, 5, 10));
            Text text = new Text(message);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: rgb(15,125,242); -fx-background-radius: 10;");
            textFlow.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
            text.setFill(javafx.scene.paint.Color.color(0.934, 0.934, 0.934));
            hBox.getChildren().add(textFlow);
            messageBox.getChildren().add(hBox);
            Message messageObj = new Message("communication", message,"all");
            messageObj.setClientData(new ClientData(UserData.getUsername()));
            client.sendMessage(messageObj);
            textField.clear();
        }


    }

    public static void addLabel(String msgFromServer,VBox vBox){
        System.out.println("Message -----from------ server: "+msgFromServer);
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text=new Text(msgFromServer);
        TextFlow textFlow=new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235); -fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client.setMessageView(messageBox);
    }
}
