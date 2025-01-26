package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

import java.util.*;

import static game.rise_of_valor.models.Sprite.FLY;
import static javafx.scene.text.FontWeight.BOLD;

public class Character {

    //world position of character
    public double worldPositionX;// world position of character in game world
    public double worldPositionY;// world position of character in game world


    //sprite size of character
    protected double spriteWidth; // spriteWidth is character width that is cropped from the sprite sheet
    protected double spriteHeight; // spriteHeight is character height that is cropped from the sprite sheet
    protected double scaleFactor; // Scale factor for the sprite to make it bigger or smaller

//    protected int spriteX = 700; // spriteX is the x position of the character in the sprite sheet
//    protected int spriteY = 1030; // spriteY is the y position of the character in the sprite sheet


    //body box of character
    protected double bodyX, bodyY; // Body position of the character
    protected int bodyOffsetX = 8; // Offset of the body box from the world position
    protected int bodyOffsetY = 10; // Offset of the body box from the world position
    protected double bodyWidth = 30; // Width of the body box
    protected double bodyHeight = 35;// Height of the body box


    protected String moveMode; // Character movement mode (e.g., walk or fly) to determine the sprite animation
    protected boolean isMoving = false;


    //character sprite animation
    protected List<Image> movement = new ArrayList<>(); // List of movement sprites
    protected int movementSpriteCount = 0; // Total sprite number of currently loaded sprite
    protected List<Image> idle = new ArrayList<>(); // List of idle sprites
    protected int idleSpriteCount = 0; // Total sprite number of currently loaded sprite
    protected int spriteCount = 0; // Total sprite number of currently loaded sprite
    protected int currentSprite = 0; // Current sprite index
    double animationTime = 0; // Animation time in seconds
    double baseFrameDuration = 1; // Base duration of each frame in seconds
    int spriteAnimationFactor = 32; // Sprite animation factor


    protected int speed = 100; // Speed of the character in pixels per second default 100

    protected boolean facingLeft = false; // Character facing direction


    protected final RadialGradient shadowGradient; // Shadow gradient for the character


    private int life;
    private final List<DamageNumber> damageNumbers = new ArrayList<>();
    private final Random random = new Random();


    public Character(int worldPositionX, int worldPositionY) {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;

        scaleFactor = 15 * 0.5; // default scale factor
        if (!movement.isEmpty()) {
            spriteWidth = ( movement.getFirst().getWidth()/scaleFactor);
            spriteHeight =( movement.getFirst().getHeight()/scaleFactor);
        }

        life = 100; // default life of character


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
        // Calculate frame duration based on speed
        double frameDuration = baseFrameDuration / (spriteAnimationFactor / 32.0);

        // Update animation time
        animationTime += deltaTime;
        if (animationTime >= frameDuration) {
            currentSprite++;
            animationTime -= frameDuration;
//            System.out.println("Current sprite: " + currentSprite);
        }
        if (currentSprite > spriteCount) {
            currentSprite = 0;
        }

        // Update damage numbers
        Iterator<DamageNumber> iterator = damageNumbers.iterator();
        while (iterator.hasNext()) {
            DamageNumber damageNumber = iterator.next();
            damageNumber.update(deltaTime);
            if (damageNumber.isExpired()) {
                iterator.remove();
            }
        }
    }

    public void draw(GraphicsContext gc) {
        List<Image> sprites = moveMode.equals(FLY) ? movement : (isMoving ? movement : idle);


        if (currentSprite < sprites.size()) {
            Image sprite = sprites.get(currentSprite);
//            int spriteWidth = (int) sprite.getWidth();
//            int spriteHeight = (int) sprite.getHeight();



            if (!moveMode.equals(FLY)) { // Only draw shadow for walking characters

                // Calculate shadow position based on player's position
                double shadowX = worldPositionX + 5;
                double shadowY = worldPositionY + spriteHeight - 10;

                // Draw realistic shadow under the character
                gc.setFill(shadowGradient);
                gc.fillOval(shadowX, shadowY, 30, 15);
            }

            // Draw the sprite
            if (facingLeft) {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, 0, 0, spriteWidth * scaleFactor, spriteHeight * scaleFactor, -worldPositionX - spriteWidth, worldPositionY, spriteWidth, spriteHeight);
                gc.restore();
            } else {
                gc.drawImage(sprite, 0, 0, spriteWidth * scaleFactor, spriteHeight * scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
            }
            // Draw body box
//            gc.setStroke(Color.RED);
//            gc.strokeRect(bodyX, bodyY, bodyWidth, bodyHeight);

            // Draw damage numbers
            for (DamageNumber damageNumber : damageNumbers) {
                damageNumber.draw(gc);
            }
        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
        }
    }


    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public double getCharacterWidth() {
        return spriteWidth;
    }

    public double getCharacterHeight() {
        return spriteHeight;
    }

    public void takeDamage(int damage) {
        life -= damage;

        // Add a new damage number
        double offsetX = random.nextDouble() * 20 - 10; // Random offset between -10 and 10
        double offsetY = random.nextDouble() * 20 - 10; // Random offset between -10 and 10
        damageNumbers.add(new DamageNumber(worldPositionX + offsetX, worldPositionY + offsetY, damage));

    }

    public int getLife() {
        return life;
    }


    /**
     * getBody method returns the body of the character
     *
     * @return double[] array containing:
     * bodyX = x-coordinate,
     * bodyY = y-coordinate,
     * bodyWidth = width of the body,
     * bodyHeight = height of the body.
     */
    public double[] getBody() {
        return new double[]{bodyX, bodyY, bodyWidth, bodyHeight};
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }


    private static class DamageNumber {
        private static final double DURATION = 1.0; // Duration in seconds
        private static final double FADE_OUT_TIME = 0.5; // Fade out time in seconds

        private final double x;
        private final double y;
        private final int damage;
        private double timeElapsed;

        public DamageNumber(double x, double y, int damage) {
            this.x = x;
            this.y = y;
            this.damage = damage;
            this.timeElapsed = 0;
        }

        public void update(double deltaTime) {
            timeElapsed += deltaTime;
        }

        public boolean isExpired() {
            return timeElapsed >= DURATION;
        }

        public void draw(GraphicsContext gc) {
            double alpha = 1.0;
            if (timeElapsed > DURATION - FADE_OUT_TIME) {
                alpha = (DURATION - timeElapsed) / FADE_OUT_TIME;
            }

            gc.setGlobalAlpha(alpha);
            gc.setFont(Font.font("Arial", BOLD, 12));
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeText(String.valueOf(damage), x, y);
            gc.fillText(String.valueOf(damage), x, y);
            gc.setGlobalAlpha(1.0); // Reset alpha
        }
    }
}