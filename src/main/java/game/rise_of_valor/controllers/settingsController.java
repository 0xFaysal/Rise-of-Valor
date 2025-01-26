package game.rise_of_valor.controllers;

import game.rise_of_valor.Main;
import game.rise_of_valor.shareData.localCustomData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static game.rise_of_valor.shareData.localCustomData.cursor;

public class settingsController implements Initializable {

    @FXML
    private Slider MusicSlider;
    @FXML
    private Slider VfxSlider;

    @FXML
    void backBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-view.fxml"));
        stage.getScene().setRoot(loader.load());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Example: Print slider value on change
        MusicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Volume: " + newVal.intValue());
        });

        VfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {

            System.out.println("VFX Volume: " + newVal.intValue());
        });
    }


    @FXML
    void cursor1(ActionEvent event) {
        System.out.println("Cursor 1");
        localCustomData.cursor = "assets/cursor/cursor1.png";
        Scene scene = MusicSlider.getScene();
        scene.setCursor(new ImageCursor(new Image(Main.class.getResourceAsStream(cursor))));

    }

    @FXML
    void cursor2(ActionEvent event) {
        System.out.println("Cursor 2");
        localCustomData.cursor = "assets/cursor/cursor2.png";
        Scene scene = MusicSlider.getScene();
        scene.setCursor(new ImageCursor(new Image(Main.class.getResourceAsStream(cursor))));

    }

    @FXML
    void cursor3(ActionEvent event) {
        System.out.println("Cursor 4");
        localCustomData.cursor = "assets/cursor/cursor4.png";
        Scene scene = MusicSlider.getScene();
        scene.setCursor(new ImageCursor(new Image(Main.class.getResourceAsStream(cursor))));

    }

    @FXML
    void cursor4(ActionEvent event) {
        System.out.println("Cursor 3");
        localCustomData.cursor = "assets/cursor/cursor3.png";
        Scene scene = MusicSlider.getScene();
        scene.setCursor(new ImageCursor(new Image(Main.class.getResourceAsStream(cursor))));

    }

    @FXML
    void cursor5(ActionEvent event) {
        System.out.println("Cursor 5");
        localCustomData.cursor = "assets/cursor/cursor5.png";
        Scene scene = MusicSlider.getScene();
        scene.setCursor(new ImageCursor(new Image(Main.class.getResourceAsStream(cursor))));

    }

    @FXML
    void cursor6(ActionEvent event) {
        System.out.println("Cursor 6");
        localCustomData.cursor = "assets/cursor/cursor3.png";
        Scene scene = MusicSlider.getScene();
        scene.setCursor(new ImageCursor(new Image(Main.class.getResourceAsStream(cursor))));

    }

    @FXML
    void cursor7(ActionEvent event) {
        System.out.println("Cursor 7");
        localCustomData.cursor = "assets/cursor/cursor7.png";
        Scene scene = MusicSlider.getScene();
        scene.setCursor(new ImageCursor(new Image(Main.class.getResourceAsStream(cursor))));

    }

    @FXML
    void cursor8(ActionEvent event) {
        System.out.println("Cursor 8");
        localCustomData.cursor = "assets/cursor/cursor8.png";
        Scene scene = MusicSlider.getScene();
        scene.setCursor(new ImageCursor(new Image(Main.class.getResourceAsStream(cursor))));

    }

}


