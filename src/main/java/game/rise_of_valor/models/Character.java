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

import static game.rise_of_valor.models.Sprite.FLY;

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

    protected double bodyX, bodyY;
    protected double bodyWidth = 30;
    protected double bodyHeight = 35;



    protected String model;
    protected boolean isMoving = false;

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
    int spriteAnimationVector = 32;

    private int life;

    public Character(int worldPositionX, int worldPositionY) {
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;

        scaleFactor = 15;
        spriteWidth = 600 / scaleFactor;// sprite width default 600
        spriteHeight = 800 / scaleFactor;

        life =100;


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
        double frameDuration = baseFrameDuration / (spriteAnimationVector / 32.0);

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
        List<Image> sprites=model.equals(FLY) ? movement : (isMoving ? movement : idle);


        if (currentSprite < sprites.size()) {
            Image sprite = sprites.get(currentSprite);




            if (!model.equals(FLY)) {


                // Calculate shadow position based on player's position
                double shadowX = worldPositionX + 5;
                double shadowY = worldPositionY + spriteHeight - 10;

                // Draw realistic shadow under the character
                gc.setFill(shadowGradient);
                gc.fillOval(shadowX, shadowY, 30, 15);
            }

            //draw box around character
//            gc.setStroke(Color.RED);
//            gc.strokeRect(worldPositionX, worldPositionY, spriteWidth, spriteHeight);


            if (facingLeft) {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, spriteX, spriteY, spriteWidth * scaleFactor, spriteHeight * scaleFactor, -worldPositionX - spriteWidth, worldPositionY, spriteWidth, spriteHeight);
                gc.restore();
            } else {
                gc.drawImage(sprite, spriteX, spriteY, spriteWidth * scaleFactor, spriteHeight * scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
            }

            //draw body box
//            gc.setStroke(Color.RED);
//            gc.strokeRect(bodyX, bodyY, bodyWidth, bodyHeight);
//            gc.rect(bodyX, bodyY, bodyWidth, bodyHeight);


        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
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

    public void takeDamage(int damage){
        life-= damage;
    }

    public int getLife(){
        return life;
    }
}