package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Character {

    //world position of character
    public double worldPositionX;
    public double worldPositionY;


    //sprite size of character
    int spriteWidth;
    int spriteHeight;
    int scaleFactor;
    int spriteX = 700;
    int spriteY = 1030;


    //movement actions of character CONSTANTS
    protected static final String WALK = "walk";
    protected static final String FLY = "fly";


    protected String SPRITE_PATH_TEMPLATE;
    List<Image> movement = new ArrayList<>();
    int movementSpriteCount = 0;
    List<Image> idle = new ArrayList<>();
    int idleSpriteCount = 0;

    int spriteCount = 0;
    int currentSprite = 0;
    double animationTime = 0;
    double baseFrameDuration = 1; // Base duration of each frame in seconds

    int speed = 100;
    double diagonalSpeed;
    boolean facingLeft = false;

    //shadow gradient
    protected final RadialGradient shadowGradient;

    //sprite animation factor
    int spriteAnimetionFector = 32;

    public Character(int worldPositionX, int worldPositionY) {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;

        scaleFactor = 15;
        spriteWidth = 600 / scaleFactor;// sprite width default 600
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
        // Calculate frame duration based on speed
        double frameDuration = baseFrameDuration / (spriteAnimetionFector / 32.0);

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
//        System.out.println("Update Current sprite: " + currentSprite);
    }

    public void draw(GraphicsContext gc) {
        List<Image> sprites = movement;
        if (currentSprite < sprites.size()) {
            Image sprite = sprites.get(currentSprite);
//            double width = sprite.getWidth();
//            double height = sprite.getHeight();
//            double scaledSize = tileSize * tileScale;




            // Calculate shadow width using a sine wave function for smooth animation
            double time = System.currentTimeMillis() / 1000.0;
            double shadowWidth = 40 + 10 * Math.sin(time * 1 * Math.PI); // Adjust amplitude and frequency as needed
            double shadowXOffset = (spriteWidth - shadowWidth) / 2;

            // Calculate shadow position based on player's position
            double shadowX = worldPositionX + shadowXOffset;
            double shadowY = worldPositionY + spriteHeight - 10;

            // Draw realistic shadow under the character
            gc.setFill(shadowGradient);
            gc.fillOval(shadowX, shadowY, shadowWidth, 15);

            //draw box around character
            gc.setStroke(Color.RED);
            gc.strokeRect(worldPositionX, worldPositionY, spriteWidth, spriteHeight);


            if (facingLeft) {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, spriteX, spriteY, spriteWidth * scaleFactor, spriteHeight * scaleFactor, -worldPositionX - spriteWidth, worldPositionY, spriteWidth, spriteHeight);
                gc.restore();
            } else {
                gc.drawImage(sprite, spriteX, spriteY, spriteWidth * scaleFactor, spriteHeight * scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
            }


        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
        }
    }

    protected void loadSprites(String action, int count, List<Image> spriteList, int playerCharacterId) {

        if(action.equals(FLY)) {
            spriteX-=200;
            spriteWidth = 900 / scaleFactor;
            spriteHeight = 800 / scaleFactor;
        }
        for (int i = 0; i <= count; i++) {
            try {
                spriteList.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                        String.format(SPRITE_PATH_TEMPLATE, playerCharacterId, action, i)))));
            } catch (Exception e) {
                System.err.println("Error loading sprite: " + e.getMessage());
            }
        }
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }
}