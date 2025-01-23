package game.rise_of_valor.shareData;

import game.rise_of_valor.network.client.Client;
import game.rise_of_valor.utils.LoadSprite;
import javafx.scene.image.Image;

public class DataManager {

    LoadSprite loadSprite;
    public static Client client;
    static Image backgroundTimer;

    public DataManager() {

        loadSprite = new LoadSprite();
        loadSprite.loadPlayer(1);
        backgroundTimer = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/timer-bg1.png"));
    }
    public LoadSprite getLoadSprite() {
        return loadSprite;
    }

    public static Image getBackgroundTimer() {
        return backgroundTimer;
    }
}
