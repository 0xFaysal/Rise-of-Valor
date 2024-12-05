package game.rise_of_valor.game_engine;

import game.rise_of_valor.models.Player;
import game.rise_of_valor.models.Weapon;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

import static game.rise_of_valor.data.MapData.MAP1_HEIGHT;
import static game.rise_of_valor.data.MapData.MAP1_WIDTH;

public class GameWorld {

    public final int CANVAS_WIDTH, CANVAS_HEIGHT;

    Canvas canvas;
    Scene scene;
    private final List<KeyCode> keys = new ArrayList<>();

    Player player;
    TileManager tileManager;

    Camera camera;

    final int WORLD_WIDTH = MAP1_WIDTH;
    final int WORLD_HEIGHT = MAP1_HEIGHT;

    public GameWorld(Canvas canvas, Scene scene) {
        this.canvas = canvas;
        this.scene = scene;
        this.CANVAS_WIDTH = (int) canvas.getWidth();
        this.CANVAS_HEIGHT = (int) canvas.getHeight();
        player = new Player(100, 100);
        tileManager = new TileManager(CANVAS_WIDTH, CANVAS_HEIGHT);
        camera = new Camera(CANVAS_WIDTH, CANVAS_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);


        // Add key press listener
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (!keys.contains(key)) {
                keys.add(0, key); // Add key to the beginning of the list
            }
        });

        // Add key release listener
        scene.setOnKeyReleased(e -> {
            KeyCode key = e.getCode();
            keys.remove(key); // Remove key when released
        });
    }

    public void update(double deltaTime) {
        player.update(scene, deltaTime, keys);
        camera.update(player.getInertiaPositionX(), player.getInertiaPositionY());
    }

    public void render(GraphicsContext gc) {
        tileManager.draw(gc, camera);
        player.draw(gc, camera);
    }
}
