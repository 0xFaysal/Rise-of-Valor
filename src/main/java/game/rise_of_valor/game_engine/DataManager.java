package game.rise_of_valor.game_engine;

import game.rise_of_valor.utils.LoadSprite;

public class DataManager {

    LoadSprite loadSprite;

    public DataManager() {
        loadSprite = new LoadSprite();
        loadSprite.loadPlayer(1);
    }
    public LoadSprite getLoadSprite() {
        return loadSprite;
    }
}
