package game.rise_of_valor.game_engine;

import game.rise_of_valor.effects.DeathEffect;
import game.rise_of_valor.models.*;
import game.rise_of_valor.models.Character;
import game.rise_of_valor.models.Enemy;
import game.rise_of_valor.utils.CustomFont;
import game.rise_of_valor.utils.LoadSprite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.*;

import static game.rise_of_valor.controllers.LoadingController.dataManager;
import static game.rise_of_valor.game_engine.MapManager.space;


public class GameWorld {
    public final int CANVAS_WIDTH, CANVAS_HEIGHT;
    private double cameraX, cameraY; // Camera position based on the player height.
    private double targetCameraX, targetCameraY; // Target camera position
    private double previousPlayerX, previousPlayerY; // To track player position changes and

    Canvas canvas;
    Scene scene;
    private final List<KeyCode> keys = new ArrayList<>();

    Player player;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();

    //    TileManager tileManager;
    MapManager mapManager;

    private final Timeline firingTimeline; // For firing bullets continuously

    private double mouseX;
    private double mouseY;


    private final List<DeathEffect> deathEffects = new ArrayList<>();

    private final TopViewManager topViewManager;

    EnemyAddingManager enemyAddingManager;
    CustomFont customFont;

    public GameWorld(Canvas canvas, Scene scene) {

        this.canvas = canvas;
        this.scene = scene;
        this.CANVAS_WIDTH = (int) canvas.getWidth();
        this.CANVAS_HEIGHT = (int) canvas.getHeight();


        customFont = new CustomFont();
        topViewManager = new TopViewManager(customFont);

        mapManager = new MapManager(3);


        LoadSprite loadSprite = dataManager.getLoadSprite();

        player = new Player(loadSprite.getPlayerSprite(), (int) (mapManager.getMapWidth() / 2), (int) (mapManager.getMapHeight() / 2));

        // Add enemies to the game world
        enemyAddingManager = new EnemyAddingManager(enemies, player, mapManager, loadSprite);

//        Random random = new Random();

        // Add enemies to the game world
//        for (int i = 0; i < 80; i++) {
//            int x = (int) (mapManager.getSpace() + random.nextInt((int) (mapManager.getMapWidth() - space)));
//            int y = (int) (mapManager.getSpace() + random.nextInt((int) (mapManager.getMapHeight() - space)));
//            enemies.add(new Enemy(loadSprite.getEnemySprite(i % 4), Math.abs(x), Math.abs(y)));
//        }


        // Initialize camera position based on player's starting position
        targetCameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
        targetCameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;

        // Clamp camera position to map boundaries
        cameraX = Math.max(0, Math.min(targetCameraX, mapManager.getMapWidth() - CANVAS_WIDTH));
        cameraY = Math.max(0, Math.min(targetCameraY, mapManager.getMapHeight() - CANVAS_HEIGHT));

        // Track initial player position
        previousPlayerX = player.worldPositionX;
        previousPlayerY = player.worldPositionY;

        // Add key press listener to add keys to the list for player movement
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (!keys.contains(key)) {
                keys.addFirst(key); // Add key to the beginning of the list
            }
        });

        // Add key release listener to remove keys
        scene.setOnKeyReleased(e -> {
            KeyCode key = e.getCode();
            keys.remove(key); // Remove key when released
        });

        // Add mouse move listener
        canvas.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            player.getGun().updateMousePosition(mouseX, mouseY);
        });

        // Add mouse click listener to fire bullets
        canvas.setOnMouseClicked(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            player.getGun().updateMousePosition(mouseX, mouseY);
            bullets.add(new Bullet(player.getGun().getGunPointX(), player.getGun().getGunPointY(), mouseX + cameraX, mouseY + cameraY));
        });


        // Create a timeline for firing bullets continuously
        firingTimeline = new Timeline(new KeyFrame(Duration.seconds(1.0 / player.getGun().getShootSpeed()), e -> {
            bullets.add(new Bullet(player.getGun().getGunPointX(), player.getGun().getGunPointY(), mouseX + cameraX, mouseY + cameraY));
        }));
        firingTimeline.setCycleCount(Timeline.INDEFINITE);

        // Add mouse press listener to start firing
        canvas.setOnMousePressed(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            player.getGun().updateMousePosition(mouseX, mouseY);
            firingTimeline.play();
        });

        // Add mouse release listener to stop firing
        canvas.setOnMouseReleased(e -> firingTimeline.stop());

        // Add mouse drag listener for continuous firing
        canvas.setOnMouseDragged(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            player.getGun().updateMousePosition(mouseX, mouseY);
        });

    }

    public void update(double deltaTime) {

        // Update player position
        player.update(scene, deltaTime, keys);

        // Update enemy positions and handle attacks
        for (Enemy enemy : enemies) {
            if (enemy.getCurrentCharacterId() == 2 || enemy.getCurrentCharacterId() == 3) {
                enemy.moveRandomly(deltaTime, mapManager, enemies);
            } else {
                enemy.moveTowards(player.worldPositionX, player.worldPositionY, deltaTime, enemies);
            }
            enemy.update(deltaTime);
            enemy.attack(deltaTime,player, enemyBullets);
        }


        // Remove enemies with life less than or equal to 0
        enemies.removeIf(enemy -> enemy.getLife() <= 0);

        // Update camera position based on player position
        if (player.worldPositionX != previousPlayerX || player.worldPositionY != previousPlayerY) {
            previousPlayerX = player.worldPositionX;
            previousPlayerY = player.worldPositionY;

            targetCameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
            targetCameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;

            targetCameraX = Math.max(0, Math.min(targetCameraX, mapManager.getMapWidth() - CANVAS_WIDTH));
            targetCameraY = Math.max(0, Math.min(targetCameraY, mapManager.getMapHeight() - CANVAS_HEIGHT));

        }


        // Update gun position based on camera position
        player.getGun().updateCameraPosition(targetCameraX, targetCameraY);


        // Smoothly interpolate the camera position towards the target position
        cameraX += (targetCameraX - cameraX) * 0.1;
        cameraY += (targetCameraY - cameraY) * 0.1;


        // Update bullets
        for (Bullet bullet : bullets) {
            bullet.update(deltaTime);
        }

        // Update enemy bullets
        for (EnemyBullet bullet : enemyBullets) {
            bullet.update(deltaTime);
        }

        // Remove bullets that are out of bounds or inactive
        bullets.removeIf(bullet -> bullet.isOutOfBounds((int) mapManager.getMapWidth(), (int) mapManager.getMapHeight()) || !bullet.isActive());
        enemyBullets.removeIf(bullet -> bullet.isOutOfBounds((int) mapManager.getMapWidth(), (int) mapManager.getMapHeight()) || !bullet.isActive());

        // Check for collisions between bullets and enemies
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.intersects(enemy.getBody())) {
                    enemy.takeDamage(player.getGun().getDamage());
                    bullet.setActive(false);
                }
            }
        }

        // Check for collisions between enemy bullets and player
        for (EnemyBullet bullet : enemyBullets) {
            if (bullet.intersects(player.getBody())) {
                player.takeDamage(10); // Example damage value
                bullet.setActive(false);
            }
        }

        // Update death effects and remove expired ones
        Iterator<DeathEffect> iterator = deathEffects.iterator();
        while (iterator.hasNext()) {
            DeathEffect effect = iterator.next();
            effect.update(deltaTime);
            if (effect.isExpired()) {
                iterator.remove();
            }
        }

        // Remove enemies with life less than or equal to 0 and add death effects
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (enemy.getLife() <= 0) {
                deathEffects.add(new DeathEffect(enemy.getBody()[0] + enemy.getBody()[2] / 2.0, enemy.worldPositionY + enemy.getBody()[3] / 2.0, enemy.getBody()[2], enemy.getBody()[3], enemy.getCurrentCharacterId()));
                enemyIterator.remove();
                topViewManager.updateKilledEnemy();
            }
        }


        // Update timer
        topViewManager.update(deltaTime); // Update timer
        topViewManager.setRemainEnemy(enemies.size()); // Update remaining enemies
        topViewManager.setPlayerLife(player.getLife()); // Update player life

        //Add enemies to the game world
        enemyAddingManager.update(deltaTime, topViewManager.getKillingRate());


    }

    public void render(GraphicsContext gc) {

        // Translate the graphics context for the camera
        gc.save();
        gc.translate(-cameraX, -cameraY);

        // Draw map
        mapManager.drawMap(gc);

        // Draw death effects
        for (DeathEffect effect : deathEffects) {
            effect.draw(gc);
        }


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

        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(gc);
        }

        // Draw enemy bullets
        for (EnemyBullet bullet : enemyBullets) {
            bullet.draw(gc);
        }

        // Restore graphics context
        gc.restore();

        // Draw timer on top of the camera view
        topViewManager.draw(gc, CANVAS_WIDTH, CANVAS_HEIGHT);
    }


    /**
     * Check if a character is visible on the screen
     *
     * @param character The character to check
     * @return True if the character is visible, false otherwise
     */
    private boolean isVisible(Character character) {
        return character.worldPositionX + character.getPlayerWidth() > cameraX &&
                character.worldPositionX < cameraX + CANVAS_WIDTH &&
                character.worldPositionY + character.getPlayerHeight() > cameraY &&
                character.worldPositionY < cameraY + CANVAS_HEIGHT;
    }


    public void resetGameWorld() {
        player = new Player(dataManager.getLoadSprite().getPlayerSprite(), (int) (mapManager.getMapWidth() / 2), (int) (mapManager.getMapHeight() / 2));
        enemies.clear();
        Random random = new Random();
        for (int i = 0; i < 80; i++) {
            int x = (int) (mapManager.getSpace() + random.nextInt((int) (mapManager.getMapWidth() - space)));
            int y = (int) (mapManager.getSpace() + random.nextInt((int) (mapManager.getMapHeight() - space)));
            enemies.add(new Enemy(dataManager.getLoadSprite().getEnemySprite(i % 4), Math.abs(x), Math.abs(y)));
        }
        targetCameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
        targetCameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;
        cameraX = Math.max(0, Math.min(targetCameraX, mapManager.getMapWidth() - CANVAS_WIDTH));
        cameraY = Math.max(0, Math.min(targetCameraY, mapManager.getMapHeight() - CANVAS_HEIGHT));
        previousPlayerX = player.worldPositionX;
        previousPlayerY = player.worldPositionY;
        keys.clear();
        bullets.clear();
        deathEffects.clear();
        topViewManager.reset();
    }
}