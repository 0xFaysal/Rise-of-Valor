package game.rise_of_valor.game_engine;

import game.rise_of_valor.models.Enemy;
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
    ArrayList<Enemy> enemies = new ArrayList<>();
    TileManager tileManager;

    public GameWorld(Canvas canvas, Scene scene) {
        this.canvas = canvas;
        this.scene = scene;
        this.CANVAS_WIDTH = (int) canvas.getWidth();
        this.CANVAS_HEIGHT = (int) canvas.getHeight();
        player = new Player(100, 100);
        tileManager = new TileManager(CANVAS_WIDTH, CANVAS_HEIGHT);
        enemies.add(new Enemy(1,200, 200));
        enemies.add(new Enemy(2,700, 300));
        enemies.add(new Enemy(3,1300, 300));
        enemies.add(new Enemy(4,400, 800));



        // Initialize camera position based on player's starting position
        cameraX = Math.max(0, Math.min(player.worldPositionX - CANVAS_WIDTH / 2.0, MAP1_WIDTH - CANVAS_WIDTH));
        cameraY = Math.max(0, Math.min(player.worldPositionY - CANVAS_HEIGHT / 2.0, MAP1_HEIGHT - CANVAS_HEIGHT));

        // Track initial player position
        previousPlayerX = player.worldPositionX;
        previousPlayerY = player.worldPositionY;

        // Add key press listener
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (!keys.contains(key)) {
                keys.addFirst(key); // Add key to the beginning of the list
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

        // Update enemy position
//        enemy.moveTowards(player.worldPositionX, player.worldPositionY,player.getPlayerWidth(),player.getPlayerHeight(), deltaTime);
//        enemy2.moveTowards(player.worldPositionX, player.worldPositionY,player.getPlayerWidth(),player.getPlayerHeight(), deltaTime);
//        enemy3.moveTowards(player.worldPositionX, player.worldPositionY,player.getPlayerWidth(),player.getPlayerHeight(), deltaTime);
//
//        enemy2.update(deltaTime);
//        enemy3.update(deltaTime);
//        enemy.update(deltaTime);
        for (Enemy enemy : enemies) {
            enemy.moveTowards(player.worldPositionX, player.worldPositionY, player.getPlayerWidth(), player.getPlayerHeight(), deltaTime, enemies);
            enemy.update(deltaTime);
        }

        // Update camera position only if the player moved
        if (player.worldPositionX != previousPlayerX || player.worldPositionY != previousPlayerY) {
            previousPlayerX = player.worldPositionX;
            previousPlayerY = player.worldPositionY;

            // Center the camera on the player
            cameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
            cameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;

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

        // Determine the drawing order based on the y-position
        for (Enemy enemy : enemies) {
            if (player.worldPositionY+player.getPlayerHeight() > enemy.worldPositionY+enemy.getPlayerHeight()) {

                // Draw the enemy first, then the player
                enemy.draw(gc);
                player.draw(gc);
            } else {
                // Draw the player first, then the enemy
                player.draw(gc);
                enemy.draw(gc);
            }
        }

        // Restore graphics context
        gc.restore();
    }
}
