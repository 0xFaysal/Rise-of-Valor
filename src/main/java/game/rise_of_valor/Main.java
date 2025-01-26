package game.rise_of_valor;

import game.rise_of_valor.controllers.ServerConnectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static game.rise_of_valor.shareData.DataManager.client;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/intro.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/loading.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/login-registration.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/lobby-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/game-play.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/shop-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/winBG-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/settings-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/waiting-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ride of Valor");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("assets/image/gameLogoLoginPage.png")));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(this::handleWindowClose);

    }

    private void handleWindowClose(WindowEvent event) {
        System.exit(0);
    }




    public static void main(String[] args) {
        launch();
    }
}