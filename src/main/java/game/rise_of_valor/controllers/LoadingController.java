package game.rise_of_valor.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {


    @FXML
    private Pane mainPane;

    @FXML
    private ProgressBar progressbar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), mainPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
