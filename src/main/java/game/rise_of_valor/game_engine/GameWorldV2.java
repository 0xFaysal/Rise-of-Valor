package game.rise_of_valor.game_engine;

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

//import static game.rise_of_valor.data.MapData.mapManager.getMapHeight();
//import static game.rise_of_valor.data.MapData.mapManager.getMapWidth();

public class GameWorldV2 {
    public final int CANVAS_WIDTH, CANVAS_HEIGHT;

    private double cameraX, cameraY; // Camera position
    private double targetCameraX, targetCameraY; // Target camera position
    private double previousPlayerX, previousPlayerY; // To track player position changes

    Canvas canvas;
    Scene scene;
    private final List<KeyCode> keys = new ArrayList<>();

    Player player;
    ArrayList<BaseEnemy> enemies = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    private final List<EnemyBullet> enemyBullets = new ArrayList<>();

    //    TileManager tileManager;
    MapManager mapManager;
    private long lastShotTime = 0;
    private Timeline firingTimeline;

    private double mouseX;
    private double mouseY;


    private final List<DeathEffect> deathEffects = new ArrayList<>();

    private TopViewManager topViewManager;
    CustomFont customFont ;

    public GameWorldV2(Canvas canvas, Scene scene) {
        System.out.println("GameWorld created");
        this.canvas = canvas;
        this.scene = scene;
        this.CANVAS_WIDTH = (int) canvas.getWidth();
        this.CANVAS_HEIGHT = (int) canvas.getHeight();
        customFont = new CustomFont();
        topViewManager = new TopViewManager(customFont);



//        tileManager = new TileManager(CANVAS_WIDTH, CANVAS_HEIGHT);
        mapManager = new MapManager(3);


        LoadSprite loadSprite = dataManager.getLoadSprite();

        player = new Player(loadSprite.getPlayerSprite(), (int) (mapManager.getMapWidth() / 2), (int) (mapManager.getMapHeight() / 2));

        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            int x = random.nextInt((int) mapManager.getMapWidth());
            int y = random.nextInt((int) mapManager.getMapHeight());
            int enemyId = i % 3; // Cycle through IDs 1 to 4

            switch (enemyId) {
                case 0, 3 -> enemies.add(new ChaserEnemy(loadSprite.getEnemySprite(enemyId), x, y));
                case 1 -> enemies.add(new TripleShooterEnemy(loadSprite.getEnemySprite(enemyId), x, y));
                case 2 -> enemies.add(new ShooterEnemy(loadSprite.getEnemySprite(enemyId), x, y));
            }
        }

        // Initialize camera position based on player's starting position
        targetCameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
        targetCameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;

        // Clamp camera position to map boundaries
        cameraX = Math.max(0, Math.min(targetCameraX, mapManager.getMapWidth() - CANVAS_WIDTH));
        cameraY = Math.max(0, Math.min(targetCameraY, mapManager.getMapHeight() - CANVAS_HEIGHT));
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

        // Add mouse move listener
        canvas.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            player.getGun().updateMousePosition(mouseX, mouseY);
        });

        canvas.setOnMouseClicked(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            player.getGun().updateMousePosition(mouseX, mouseY);
            bullets.add(new Bullet(player.getGun().getGunPointX(), player.getGun().getGunPointY(), mouseX + cameraX, mouseY + cameraY));
        });


        firingTimeline = new Timeline(new KeyFrame(Duration.seconds(1.0 / player.getGun().getShootSpeed()), e -> {
            bullets.add(new Bullet(player.getGun().getGunPointX(), player.getGun().getGunPointY(), mouseX + cameraX, mouseY + cameraY));
        }));
        firingTimeline.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMousePressed(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            player.getGun().updateMousePosition(mouseX, mouseY);
            firingTimeline.play();
        });

        canvas.setOnMouseReleased(e -> firingTimeline.stop());


        canvas.setOnMouseDragged(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            player.getGun().updateMousePosition(mouseX, mouseY);
        });

    }

    public void update(double deltaTime) {
        // Update player position
        player.update(scene, deltaTime, keys);

        // Update enemies
        for (BaseEnemy enemy : enemies) {
            enemy.move(deltaTime, player, enemies);
            enemy.update(deltaTime, player, enemyBullets);
        }

        // Update enemy bullets
        for (EnemyBullet bullet : enemyBullets) {
            bullet.update(deltaTime);
        }

        // Remove enemies with life less than or equal to 0
        enemies.removeIf(enemy -> enemy.getLife() <= 0);

        if (player.worldPositionX != previousPlayerX || player.worldPositionY != previousPlayerY) {
            previousPlayerX = player.worldPositionX;
            previousPlayerY = player.worldPositionY;

            targetCameraX = player.worldPositionX - CANVAS_WIDTH / 2.0;
            targetCameraY = player.worldPositionY - CANVAS_HEIGHT / 2.0;

            targetCameraX = Math.max(0, Math.min(targetCameraX, mapManager.getMapWidth() - CANVAS_WIDTH));
            targetCameraY = Math.max(0, Math.min(targetCameraY, mapManager.getMapHeight() - CANVAS_HEIGHT));

        }

//        player.updateCameraPosition(targetCameraX, targetCameraY);
        player.getGun().updateCameraPosition(targetCameraX, targetCameraY);
//        player.updateGunBoxAngle();

        // Smoothly interpolate the camera position towards the target position
        cameraX += (targetCameraX - cameraX) * 0.1;
        cameraY += (targetCameraY - cameraY) * 0.1;

//        cameraX = targetCameraX;
//        cameraY = targetCameraY;

        // Update bullets
        for (Bullet bullet : bullets) {
            bullet.update(deltaTime);
        }

        // Remove bullets that are out of bounds or inactive
        bullets.removeIf(bullet -> bullet.isOutOfBounds((int)mapManager.getMapWidth(), (int)mapManager.getMapHeight()) || !bullet.isActive());

        // Remove out-of-bounds or inactive bullets
        enemyBullets.removeIf(bullet -> bullet.isOutOfBounds((int) mapManager.getMapWidth(), (int) mapManager.getMapHeight()) || !bullet.isActive());

        // Check for collisions between enemy bullets and the player
        for (EnemyBullet bullet : enemyBullets) {
            if (bullet.intersects(player.getBody())) {
                player.takeDamage(bullet.getDamage());
                bullet.deactivate();
            }
        }

//        // Remove bullets that are out of bounds
//        bullets.removeIf(bullet -> bullet.getX() < 0 || bullet.getX() > mapManager.getMapWidth() ||
//                bullet.getY() < 0 || bullet.getY() > mapManager.getMapHeight());

        // Check for collisions between bullets and enemies
        for (Bullet bullet : bullets) {
            for (BaseEnemy enemy : enemies) {
                if (bullet.intersects(enemy.getBody())) {
                    enemy.takeDamage(player.getGun().getDamage());
                    bullet.setActive(false);
                }
            }
        }

        // Update death effects
        Iterator<DeathEffect> iterator = deathEffects.iterator();
        while (iterator.hasNext()) {
            DeathEffect effect = iterator.next();
            effect.update(deltaTime);
            if (effect.isExpired()) {
                iterator.remove();
            }
        }

        // Remove enemies with life less than or equal to 0 and add death effects
        Iterator<BaseEnemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            BaseEnemy enemy = enemyIterator.next();
            if (enemy.getLife() <= 0) {
                deathEffects.add(new DeathEffect(enemy.getBody()[0] + enemy.getBody()[2]/2.0, enemy.worldPositionY + enemy.getBody()[3]/2.0, enemy.getBody()[2], enemy.getBody()[3], enemy.getCurrentCharacterId()));

                enemyIterator.remove();
                topViewManager.updateKilledEnemy();
            }
        }

        // Update timer
        topViewManager.update(deltaTime);
        topViewManager.setRemainEnemy(enemies.size());
        topViewManager.setPlayerLife(player.getLife());

//        System.out.println("Enemies: " + enemies.size());
//        System.out.println("Bullets: " + bullets.size());

    }

    public void render(GraphicsContext gc) {
        // Translate the graphics context for the camera
        gc.save();
        gc.translate(-cameraX, -cameraY);

        // Render only the visible part of the tiles
//        tileManager.draw(gc, cameraX, cameraY, CANVAS_WIDTH, CANVAS_HEIGHT);
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



        // Restore graphics context
        gc.restore();

        // Draw timer on top of the camera view
        topViewManager.draw(gc, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    private boolean isVisible(Character character) {
        return character.worldPositionX + character.getPlayerWidth() > cameraX &&
                character.worldPositionX < cameraX + CANVAS_WIDTH &&
                character.worldPositionY + character.getPlayerHeight() > cameraY &&
                character.worldPositionY < cameraY + CANVAS_HEIGHT;
    }
}