package game.rise_of_valor.game_engine;

import game.rise_of_valor.utils.LoadSprite;
import javafx.scene.image.Image;

public class DataManager {

    LoadSprite loadSprite;
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
