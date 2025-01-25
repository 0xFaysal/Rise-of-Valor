package game.rise_of_valor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.application.Application;


import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {

    @FXML
    private Slider MusicSlider;
    @FXML
    private Slider VfxSlider;

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

    public static void main(String[] args) {
        javafx.application.Application.launch(SliderApp.class);
    }
}


class SliderApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/slider.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slider Example");
        primaryStage.show();
    }

}