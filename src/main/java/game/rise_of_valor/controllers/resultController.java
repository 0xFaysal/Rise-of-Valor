package game.rise_of_valor.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static game.rise_of_valor.controllers.LoadingController.userData;

public class resultController implements Initializable {

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

//    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coinsLabel.setText(""+userData.getCoin());
    }

    public void setKillNum(int killNum) {
        this.killLabel.setText(""+killNum);
    }

    private void closeWindow() {
       // Stage stage = (Stage) continueButton.getScene().getWindow();
        //stage.close();
    }


    @FXML
    void continueBtn(ActionEvent event) throws IOException {
        Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-view.fxml"));
        Pane lobbyView = loader.load();
        stage.getScene().setRoot(lobbyView);
    }
}
