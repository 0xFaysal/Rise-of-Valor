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

    public Player(int inertiaPositionX, int inertiaPositionY) {
        super(inertiaPositionX, inertiaPositionY);
        speed = 300;
        movementSpriteCount = 7;
        for (int i = 0; i <= movementSpriteCount; i++) {
            movement.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                String.format(SPRITE_PATH_TEMPLATE, playerCharacterId, WALK, i)))));
        }
        idleSpriteCount = 5;
        for (int i = 0; i <= idleSpriteCount; i++) {
            idle.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                String.format(SPRITE_PATH_TEMPLATE, playerCharacterId, IDLE, i)))));
        }
    }

    public void update(Scene scene, double deltaTime, List<KeyCode> keys) {
        isMoving = false;
        if (!keys.isEmpty()) {
            KeyCode key = keys.getFirst();
            if (key == KeyCode.W || key == KeyCode.UP) {
                inertiaPositionY -= (int) (speed * deltaTime);
                isMoving = true;
            }
            if (key == KeyCode.S || key == KeyCode.DOWN) {
                inertiaPositionY += (int) (speed * deltaTime);
                isMoving = true;
            }
            if (key == KeyCode.A || key == KeyCode.LEFT) {
                inertiaPositionX -= (int) (speed * deltaTime);
                setFacingLeft(true);
                isMoving = true;
            }
            if (key == KeyCode.D || key == KeyCode.RIGHT) {
                inertiaPositionX += (int) (speed * deltaTime);
                setFacingLeft(false);
                isMoving = true;
            }
        }

        // Clamp player position within map boundaries
        inertiaPositionX = Math.max(0, Math.min(inertiaPositionX, MAP1_WIDTH - scaledTileSize));
        inertiaPositionY = Math.max(0, Math.min(inertiaPositionY, MAP1_HEIGHT - scaledTileSize));

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
            double width = sprite.getWidth();
            double height = sprite.getHeight();
            double scaledSize = tileSize * tileScale;

            int shadowWidth = 35;
            int shadowXOffset = 45;

            // Draw shadow of the character under the character
//            gc.setFill(Color.rgb(0, 0, 0, 0.4));
//            gc.fillOval(inertiaPositionX+shadowWidth + 35, inertiaPositionY+scaledSize-30, shadowWidth, 10);

            // Draw realistic shadow under the character
            RadialGradient gradient = new RadialGradient(
                    0, 0, inertiaPositionX + shadowWidth + 30, inertiaPositionY + scaledSize - 30, shadowWidth,
                    false, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.rgb(0, 0, 0, 0.5)),
                    new Stop(0.5, Color.rgb(0, 0, 0, 0.3)),
                    new Stop(0.7, Color.rgb(0, 0, 0, 0.2)),
                    new Stop(1, Color.rgb(0, 0, 0, 0.1))
            );
            gc.setFill(gradient);
            gc.fillOval(inertiaPositionX + shadowWidth + shadowXOffset, inertiaPositionY + scaledSize - 30, shadowWidth, 10);


            if (facingLeft) {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, -inertiaPositionX - scaledSize, inertiaPositionY, scaledSize, scaledSize);
                gc.restore();
            } else {
                gc.drawImage(sprite, 0, 0, width, height, inertiaPositionX, inertiaPositionY, scaledSize, scaledSize);
            }
        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
        }
    }
}