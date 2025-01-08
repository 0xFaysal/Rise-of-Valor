package game.rise_of_valor.models;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.List;

import static game.rise_of_valor.game_engine.MapManager.*;

public class Player extends Character {
    private boolean isMoving = false;



    private double handPositionX;
    private double handPositionY;

    private Gun gun;




    public Player(Sprite sprite, int inertiaPositionX, int inertiaPositionY) {
        super(inertiaPositionX, inertiaPositionY);

        gun = new Gun("gun4", 20, 10);


        speed = 200;
        scaleFactor = 10;
        spriteWidth = 600 / scaleFactor;
        spriteHeight = 800 / scaleFactor;
        spriteAnimationFactor = 250;


        this.movementSpriteCount = 7;
        movement = sprite.movement;


        this.idleSpriteCount = 5;
        idle = sprite.idle;

        handPositionX = worldPositionX + (facingLeft ? 0 : spriteWidth - 20);
        handPositionY = worldPositionY + (spriteHeight / 2.0) + 10;
        gun.setHandPosition(handPositionX, handPositionY);
        gun.setGunPosition(handPositionX, handPositionY);


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
        worldPositionX = Math.max(space-60, Math.min(worldPositionX, MAP1_WIDTH - space - spriteWidth + 60));
        worldPositionY = Math.max(space, Math.min(worldPositionY, MAP1_HEIGHT - space - spriteHeight - 60));

        // Update hand position based on player's position
        handPositionX = worldPositionX + (facingLeft ? 18 : spriteWidth - 20);
        handPositionY = worldPositionY + (spriteHeight / 2.0) + 20;
        gun.setHandPosition(handPositionX, handPositionY);

        // Update gun box position based on player's position
        gun.setGunPosition(handPositionX, handPositionY);

        // Update spriteCount based on movement state
        spriteCount = isMoving ? movementSpriteCount : idleSpriteCount;

        // Update gun box angle
        gun.updateGunBoxAngle();

        // Update animation
        super.update(deltaTime);
    }



    @Override
    public void draw(GraphicsContext gc) {
        List<Image> sprites = isMoving ? movement : idle;
        if (currentSprite < sprites.size()) {
            Image sprite = sprites.get(currentSprite);


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

            // Draw box around character
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

            gun.draw(gc);

            // Draw hand
            gc.setFill(Color.BLACK);
            gc.fillOval(handPositionX-5, handPositionY-5, 10, 10);
            // draw a circle at the hand position the radius of the circle is 20
//            gc.strokeOval(handPositionX-40, handPositionY-40, 2*40, 2*40);

        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
        }


    }

    public void setGun(Gun gun){
        this.gun = gun;
    }

    public Gun getGun(){
        return gun;
    }
}