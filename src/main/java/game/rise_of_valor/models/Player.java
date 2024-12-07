package game.rise_of_valor.models;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.List;
import java.util.Objects;

import static game.rise_of_valor.data.MapData.MAP1_HEIGHT;
import static game.rise_of_valor.data.MapData.MAP1_WIDTH;

public class Player extends Character {
    private boolean isMoving = false;
    private static final String SPRITE_PATH_TEMPLATE = "/game/rise_of_valor/assets/sprites/player%d/%s_%d.png";
    private static final String WALK = "walk";
    private static final String IDLE = "idle";
    int playerCharacterId = 3;

    //sprite size
    int spriteWidth;
    int spriteHeight;
    int scaleFactor;

    //shadow gradient
    private final RadialGradient shadowGradient;

    public Player(int inertiaPositionX, int inertiaPositionY) {
        super(inertiaPositionX, inertiaPositionY);
        scaleFactor = 10;
        spriteWidth = 600/scaleFactor;
        spriteHeight = 800/scaleFactor;
        speed = 200;



        this.movementSpriteCount = 7;
        loadSprites(WALK, movementSpriteCount, movement);

        this.idleSpriteCount = 5;
        loadSprites(IDLE, idleSpriteCount, idle);

        shadowGradient = new RadialGradient(
                0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(0, 0, 0, 0.7)),
                new Stop(0.2, Color.rgb(0, 0, 0, 0.5)),
                new Stop(0.5, Color.rgb(0, 0, 0, 0.4)),
                new Stop(0.7, Color.rgb(0, 0, 0, 0.3)),
                new Stop(0.9, Color.rgb(0, 0, 0, 0.1)),
                new Stop(1, Color.rgb(0, 0, 0, 0))
        );
    }

    private void loadSprites(String action, int count, List<Image> spriteList) {
        for (int i = 0; i <= count; i++) {
            try {
                spriteList.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                        String.format(SPRITE_PATH_TEMPLATE, playerCharacterId, action, i)))));
            } catch (Exception e) {
                System.err.println("Error loading sprite: " + e.getMessage());
            }
        }
    }

    public void update(Scene scene, double deltaTime, List<KeyCode> keys) {
        isMoving = false;
        if (!keys.isEmpty()) {
            KeyCode key = keys.getFirst();
            if (key == KeyCode.W || key == KeyCode.UP) {
                worldPositionY -= (int) (speed * deltaTime);
                isMoving = true;
            }
            if (key == KeyCode.S || key == KeyCode.DOWN) {
                worldPositionY += (int) (speed * deltaTime);
                isMoving = true;
            }
            if (key == KeyCode.A || key == KeyCode.LEFT) {
                worldPositionX -= (int) (speed * deltaTime);
                setFacingLeft(true);
                isMoving = true;
            }
            if (key == KeyCode.D || key == KeyCode.RIGHT) {
                worldPositionX += (int) (speed * deltaTime);
                setFacingLeft(false);
                isMoving = true;
            }
        }

        // Clamp player position within map boundaries
        worldPositionX = Math.max(0, Math.min(worldPositionX, MAP1_WIDTH - spriteWidth));
        worldPositionY = Math.max(0, Math.min(worldPositionY, MAP1_HEIGHT - spriteHeight));

        // Update spriteCount based on movement state
        spriteCount = isMoving ? movementSpriteCount : idleSpriteCount;

        // Update animation
        super.update(deltaTime);
    }

    @Override
    public void draw(GraphicsContext gc) {
        List<Image> sprites = isMoving ? movement : idle;
        if (currentSprite < sprites.size()) {
            Image sprite = sprites.get(currentSprite);
//            double width = sprite.getWidth();
//            double height = sprite.getHeight();
//            double scaledSize = tileSize * tileScale;

            int spriteX = 700;
            int spriteY = 1030;




            // Calculate shadow width using a sine wave function for smooth animation
            double time = System.currentTimeMillis() / 1000.0;
            double shadowWidth = 40 + 10 * Math.sin(time * 1 * Math.PI); // Adjust amplitude and frequency as needed
            double shadowXOffset = (spriteWidth - shadowWidth) / 2;

            // Calculate shadow position based on player's position
            double shadowX = worldPositionX + shadowXOffset;
            double shadowY = worldPositionY + spriteHeight - 15;

            // Draw realistic shadow under the character
            gc.setFill(shadowGradient);
            gc.fillOval(shadowX, shadowY, shadowWidth, 15);

            if (facingLeft) {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, spriteX, spriteY, spriteWidth*scaleFactor, spriteHeight*scaleFactor, -worldPositionX - spriteWidth, worldPositionY, spriteWidth, spriteHeight);
                gc.restore();
            } else {
                gc.drawImage(sprite,spriteX,spriteY,spriteWidth*scaleFactor,spriteHeight*scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
            }


//            if (facingLeft) {
//                gc.save();
//                gc.scale(-1, 1);
//                gc.drawImage(sprite, -worldPositionX - scaledSize, worldPositionY, scaledSize, scaledSize);
//                gc.restore();
//            } else {
//                gc.drawImage(sprite, 0, 0, width, height, worldPositionX, worldPositionY, scaledSize, scaledSize);
//            }
        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
        }
    }
}