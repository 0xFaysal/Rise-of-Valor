package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Character {
    int inertiaPositionX;
    int inertiaPositionY;

    int tileSize = 32;
    int tileScale = 6;

    int scaledTileSize = tileSize * tileScale;

    List<Image> movement = new ArrayList<>();
    int movementSpriteCount = 0;
    List<Image> idle = new ArrayList<>();
    int idleSpriteCount = 0;

    int spriteCount = 0;
    int currentSprite = 0;
    double animationTime = 0;
    double baseFrameDuration = 1; // Base duration of each frame in seconds

    int speed = 10;
    boolean facingLeft = false;

    public Character(int inertiaPositionX, int inertiaPositionY) {
        this.inertiaPositionX = inertiaPositionX;
        this.inertiaPositionY = inertiaPositionY;
    }

    public void update(double deltaTime) {
        // Calculate frame duration based on speed
        double frameDuration = baseFrameDuration / (speed / 32.0);

        // Update animation time
        animationTime += deltaTime;
        if (animationTime >= frameDuration) {
            currentSprite++;
            animationTime -= frameDuration;
        }
        if (currentSprite > spriteCount) {
            currentSprite = 0;
        }
    }

    public void draw(GraphicsContext gc) {
        if (!movement.isEmpty()) {
            Image sprite = movement.get(currentSprite);
            double width = sprite.getWidth();
            double height = sprite.getHeight();

            //draw shadow of the character under the character
            gc.setFill(Color.rgb(0, 0, 0, 1));
            gc.fillOval(inertiaPositionX + 10, inertiaPositionY + scaledTileSize - 10, scaledTileSize - 20, 10);


            if (facingLeft) {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, -inertiaPositionX - scaledTileSize, inertiaPositionY, scaledTileSize, scaledTileSize);
                gc.restore();
            } else {
                gc.drawImage(sprite, 0, 0, width, height, inertiaPositionX, inertiaPositionY, scaledTileSize, scaledTileSize);
            }
        } else {
            System.out.println("No sprites loaded.");
        }
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }
}