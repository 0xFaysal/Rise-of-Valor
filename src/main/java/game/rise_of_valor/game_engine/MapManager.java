package game.rise_of_valor.game_engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class MapManager {

    Image map;
    double zoom = 1;
    public static double MAP1_WIDTH;
    public static double MAP1_HEIGHT;

    public static double space;
    MapManager(int mapId) {
        space=(16*4)*zoom;

     map = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/game/rise_of_valor/assets/maps/map" + mapId + ".png")));
        MAP1_WIDTH =  map.getWidth()*zoom;
        MAP1_HEIGHT =  map.getHeight()*zoom;
    }

    public void drawMap(GraphicsContext gc) {
        gc.drawImage(map, 0, 0, map.getWidth()*zoom, map.getHeight()*zoom);

    }

    public double getMapWidth() {
        return  map.getWidth()*zoom;
    }
    public double getMapHeight() {
        return  map.getHeight()*zoom;
    }

    public double getSpace() {
        return space;
    }
}
