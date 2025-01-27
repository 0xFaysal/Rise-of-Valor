package game.rise_of_valor.shareData;

import game.rise_of_valor.Main;
import game.rise_of_valor.network.client.Client;
import game.rise_of_valor.utils.LoadSprite;
import javafx.scene.image.Image;

public class DataManager {
    LoadSprite loadSprite;

    public static Client client;
    static Image backgroundTimer;
    public static Image coinIcon;


    public DataManager() {

        loadSprite = new LoadSprite();
        loadSprite.loadPlayer(1);
        backgroundTimer = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/timer-bg1.png"));
        coinIcon = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/coin.png"));


    }
    public LoadSprite getLoadSprite() {
        return loadSprite;
    }
    public static Image getBackgroundTimer() {
        return backgroundTimer;
    }

    public static Image getProfilePic(String profilePic) {

        return new Image(DataManager.class.getResourceAsStream("/game/rise_of_valor/assets/profile/" + profilePic));
    }
    public void setCoinCount(int totalCoins) {
        UserData.setCoin(totalCoins);
    }
    public static Image setProfilePic(String profilePic) {
        return new Image(Main.class.getResourceAsStream(UserData.getProfilePicName()));
    }
}
