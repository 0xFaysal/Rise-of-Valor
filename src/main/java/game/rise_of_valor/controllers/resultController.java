package game.rise_of_valor.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class resultController {

    @FXML
    private Label killLabel;

    @FXML
    private Label xpLabel;

    @FXML
    private Label coinsLabel;

//    @FXML
//    private ImageView starsImage;

    @FXML
    private Button continueBtn;

    private int killedEnimies;
    private int coinsCollected;
    private int xpGained;
    private int starsEarned;

    public void setWinData(int coins, int kill, int xp, int stars) {
        coinsCollected = coins;
        killedEnimies = kill;
        xpGained = xp;
        starsEarned = stars;

        killLabel.setText("" + killedEnimies);
        xpLabel.setText("" + xpGained);
        coinsLabel.setText("" + coinsCollected);

        // Load star image based on stars earned
//        String starImagePath = "/images/stars_" + starsEarned + ".png"; // Example: stars_3.png
//        starsImage.setImage(new Image(getClass().getResourceAsStream(starImagePath)));
    }

    @FXML
    public void initialize() {
       // continueButton.setOnAction(e -> closeWindow());
    }

    private void closeWindow() {
       // Stage stage = (Stage) continueButton.getScene().getWindow();
        //stage.close();
    }
}
