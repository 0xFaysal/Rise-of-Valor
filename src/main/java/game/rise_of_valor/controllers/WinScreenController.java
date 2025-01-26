package game.rise_of_valor.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WinScreenController {

    @FXML
    private Label coinsLabel;

    @FXML
    private Label xpLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private ImageView starsImage;

    @FXML
    private Button continueButton;

    private int coinsCollected;
    private int xpGained;
    private int playerLevel;
    private int starsEarned;

    public void setWinData(int coins, int xp, int level, int stars) {
        coinsCollected = coins;
        xpGained = xp;
        playerLevel = level;
        starsEarned = stars;

        coinsLabel.setText("Coins Collected: " + coinsCollected);
        xpLabel.setText("XP Gained: " + xpGained);
        levelLabel.setText("Level: " + playerLevel);

        // Load star image based on stars earned
        String starImagePath = "/images/stars_" + starsEarned + ".png"; // Example: stars_3.png
        starsImage.setImage(new Image(getClass().getResourceAsStream(starImagePath)));
    }

    @FXML
    public void initialize() {
        continueButton.setOnAction(e -> closeWindow());
    }

    private void closeWindow() {
        Stage stage = (Stage) continueButton.getScene().getWindow();
        stage.close();
    }
}
