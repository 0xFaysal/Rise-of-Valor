package game.rise_of_valor.game_engine;

import game.rise_of_valor.models.Character;
import game.rise_of_valor.models.Enemy;
import game.rise_of_valor.models.Player;
import game.rise_of_valor.utils.LoadSprite;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static game.rise_of_valor.game_engine.MapManager.space;

//import static game.rise_of_valor.data.MapData.mapManager.getMapHeight();
//import static game.rise_of_valor.data.MapData.mapManager.getMapWidth();

public class GameWorld {
    public final int CANVAS_WIDTH, CANVAS_HEIGHT;

    private double cameraX, cameraY; // Camera position
    private double targetCameraX, targetCameraY; // Target camera position
    private double previousPlayerX, previousPlayerY; // To track player position changes

    Canvas canvas;
    Scene scene;
    private final List<KeyCode> keys = new ArrayList<>();

    Player player;
    ArrayList<Enemy> enemies = new ArrayList<>();
//    TileManager tileManager;
    MapManager mapManager ;

    public GameWorld(Canvas canvas, Scene scene) {
        this.canvas = canvas;
        this.scene = scene;
        this.CANVAS_WIDTH = (int) canvas.getWidth();
        this.CANVAS_HEIGHT = (int) canvas.getHeight();



//        tileManager = new TileManager(CANVAS_WIDTH, CANVAS_HEIGHT);
        mapManager = new MapManager(1);

        LoadSprite loadSprite = new LoadSprite();
        loadSprite.loadPlayer(1);
        player = new Player(loadSprite.getPlayerSprite(), 1600, 400);

        Random random = new Random();

        for (int i = 0; i < 400; i++) {
            int x = (int) (mapManager.getSpace() + random.nextInt((int) (mapManager.getMapWidth() - space)));
            int y = (int) (mapManager.getSpace() + random.nextInt((int) (mapManager.getMapHeight() - space)));
            enemies.add(new Enemy(loadSprite.getEnemySprite(i % 4), Math.abs(x), Math.abs(y)));
        }

        // Initialize camera position based on player's starting position
        targetCameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
        targetCameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;

        // Clamp camera position to map boundaries
        cameraX = Math.max(0, Math.min(targetCameraX, mapManager.getMapWidth() - CANVAS_WIDTH));
        cameraY = Math.max(0, Math.min(targetCameraY, mapManager.getMapHeight() - CANVAS_HEIGHT));

        // Initialize camera position based on player's starting position
//        cameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
//        cameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;





        // Clamp camera position to map boundaries
//        cameraX = Math.max(0, Math.min(cameraX, mapManager.getMapWidth() - CANVAS_WIDTH));
//        cameraY = Math.max(0, Math.min(cameraY, mapManager.getMapHeight() - CANVAS_HEIGHT));

        // Initialize camera position based on player's starting position
//        cameraX = Math.max(0, Math.min(player.worldPositionX - CANVAS_WIDTH / 2.0, mapManager.getMapWidth() - CANVAS_WIDTH));
//        cameraY = Math.max(0, Math.min(player.worldPositionY - CANVAS_HEIGHT / 2.0, mapManager.getMapHeight() - CANVAS_HEIGHT));

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

        // Update enemy positions
        for (Enemy enemy : enemies) {
            enemy.moveTowards(player.worldPositionX, player.worldPositionY, player.getPlayerWidth(), player.getPlayerHeight(), deltaTime, enemies);
            enemy.update(deltaTime);
        }

        if (player.worldPositionX != previousPlayerX || player.worldPositionY != previousPlayerY) {
            previousPlayerX = player.worldPositionX;
            previousPlayerY = player.worldPositionY;

            targetCameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
            targetCameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;

            targetCameraX = Math.max(0, Math.min(targetCameraX, mapManager.getMapWidth() - CANVAS_WIDTH));
            targetCameraY = Math.max(0, Math.min(targetCameraY, mapManager.getMapHeight() - CANVAS_HEIGHT));
        }

        // Smoothly interpolate the camera position towards the target position
        cameraX += (targetCameraX - cameraX) * 0.1;
        cameraY += (targetCameraY - cameraY) * 0.1;
    }

    public void render(GraphicsContext gc) {
        // Translate the graphics context for the camera
        gc.save();
        gc.translate(-cameraX, -cameraY);

        // Render only the visible part of the tiles
//        tileManager.draw(gc, cameraX, cameraY, CANVAS_WIDTH, CANVAS_HEIGHT);
        mapManager.drawMap(gc);

        // Create a list to hold both the player and enemies
        List<Character> characters = new ArrayList<>();
        characters.add(player);
        characters.addAll(enemies);

        // Sort the characters by their y-position
        characters.sort(Comparator.comparingDouble(c -> c.worldPositionY + c.getPlayerHeight()));


        // Draw only visible characters
        for (Character character : characters) {
            if (isVisible(character)) {
                character.draw(gc);
            }
        }

        // Restore graphics context
        gc.restore();
    }

    private boolean isVisible(Character character) {
        return character.worldPositionX + character.getPlayerWidth() > cameraX &&
                character.worldPositionX < cameraX + CANVAS_WIDTH &&
                character.worldPositionY + character.getPlayerHeight() > cameraY &&
                character.worldPositionY < cameraY + CANVAS_HEIGHT;
    }
}