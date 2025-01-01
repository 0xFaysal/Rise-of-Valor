package game.rise_of_valor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/intro.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/introV2.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/game-play.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ride of Valor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}