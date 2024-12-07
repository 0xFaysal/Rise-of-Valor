package game.rise_of_valor.game_engine;

import game.rise_of_valor.models.Player;
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

    private double cameraX, cameraY; // Camera position
    private double previousPlayerX, previousPlayerY; // To track player position changes

    Canvas canvas;
    Scene scene;
    private final List<KeyCode> keys = new ArrayList<>();

    Player player;
    TileManager tileManager;

    public GameWorld(Canvas canvas, Scene scene) {
        this.canvas = canvas;
        this.scene = scene;
        this.CANVAS_WIDTH = (int) canvas.getWidth();
        this.CANVAS_HEIGHT = (int) canvas.getHeight();
        player = new Player(1000, 100);
        tileManager = new TileManager(CANVAS_WIDTH, CANVAS_HEIGHT);

        // Initialize camera position based on player's starting position
        cameraX = Math.max(0, Math.min(player.inertiaPositionX - CANVAS_WIDTH / 2.0, MAP1_WIDTH - CANVAS_WIDTH));
        cameraY = Math.max(0, Math.min(player.inertiaPositionY - CANVAS_HEIGHT / 2.0, MAP1_HEIGHT - CANVAS_HEIGHT));

        // Track initial player position
        previousPlayerX = player.inertiaPositionX;
        previousPlayerY = player.inertiaPositionY;

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
        // Update player position
        player.update(scene, deltaTime, keys);

        // Update camera position only if the player moved
        if (player.inertiaPositionX != previousPlayerX || player.inertiaPositionY != previousPlayerY) {
            previousPlayerX = player.inertiaPositionX;
            previousPlayerY = player.inertiaPositionY;

            // Center the camera on the player
            cameraX = player.inertiaPositionX - CANVAS_WIDTH / 2.0;
            cameraY = player.inertiaPositionY - CANVAS_HEIGHT / 2.0;

            // Clamp camera position within map boundaries
            cameraX = Math.max(0, Math.min(cameraX, MAP1_WIDTH - CANVAS_WIDTH));
            cameraY = Math.max(0, Math.min(cameraY, MAP1_HEIGHT - CANVAS_HEIGHT));
        }
    }

    public void render(GraphicsContext gc) {
        // Translate the graphics context for the camera
        gc.save();
        gc.translate(-cameraX, -cameraY);

        // Render only the visible part of the tiles
        tileManager.draw(gc, cameraX, cameraY, CANVAS_WIDTH, CANVAS_HEIGHT);

        // Render the player
        player.draw(gc);

        // Restore graphics context
        gc.restore();
    }
}
