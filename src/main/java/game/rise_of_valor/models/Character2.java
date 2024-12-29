package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.ArrayList;
import java.util.List;

import static game.rise_of_valor.models.Sprite.FLY;

public class Character2 {

    // World position of character
    public double worldPositionX;
    public double worldPositionY;

    // Sprite size of character
    private int spriteWidth;
    private int spriteHeight;
    private int scaleFactor;
    private int spriteX = 700;
    private int spriteY = 1030;

    // Model and movement states
    protected String model;
    protected boolean isMoving = false;
    private boolean facingLeft = false;

    // Sprite animation lists
    private List<Image> movement = new ArrayList<>();
    private List<Image> idle = new ArrayList<>();
    private int currentSprite = 0;
    private double animationTime = 0;
    private double baseFrameDuration = 1; // Base duration of each frame in seconds
    private int spriteAnimationVector = 32; // Default animation speed

    // Shadow gradient
    private final RadialGradient shadowGradient;

    public Character2(int worldPositionX, int worldPositionY) {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;

        scaleFactor = 15;
        spriteWidth = 600 / scaleFactor;
        spriteHeight = 800 / scaleFactor;

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

    public void update(double deltaTime) {
        // Calculate frame duration based on speed (optimized to avoid repeated calculation)
        double frameDuration = baseFrameDuration / (spriteAnimationVector / 32.0);

        // Update animation time and sprite index
        animationTime += deltaTime;
        if (animationTime >= frameDuration) {
            currentSprite++;
            animationTime -= frameDuration;
        }
        if (currentSprite >= getCurrentSpriteList().size()) {
            currentSprite = 0; // Reset to first sprite when exceeding the list
        }
    }

    private List<Image> getCurrentSpriteList() {
        // Return the correct sprite list based on movement and model state
        return model.equals(FLY) ? movement : (isMoving ? movement : idle);
    }

    public void draw(GraphicsContext gc) {
        List<Image> sprites = getCurrentSpriteList();

        if (currentSprite < sprites.size()) {
            Image sprite = sprites.get(currentSprite);

            // Draw shadow if not flying
            if (!model.equals(FLY)) {
                drawShadow(gc);
            }

            // Draw character sprite
            drawSprite(gc, sprite);
        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
        }
    }

    private void drawShadow(GraphicsContext gc) {
        double shadowX = worldPositionX + 5;
        double shadowY = worldPositionY + spriteHeight - 10;

        gc.setFill(shadowGradient);
        gc.fillOval(shadowX, shadowY, 30, 15);
    }

    private void drawSprite(GraphicsContext gc, Image sprite) {
        if (facingLeft) {
            gc.save();
            gc.scale(-1, 1);
            gc.drawImage(sprite, spriteX, spriteY, spriteWidth * scaleFactor, spriteHeight * scaleFactor, -worldPositionX - spriteWidth, worldPositionY, spriteWidth, spriteHeight);
            gc.restore();
        } else {
            gc.drawImage(sprite, spriteX, spriteY, spriteWidth * scaleFactor, spriteHeight * scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
        }
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public int getPlayerWidth() {
        return spriteWidth;
    }

    public int getPlayerHeight() {
        return spriteHeight;
    }
}
