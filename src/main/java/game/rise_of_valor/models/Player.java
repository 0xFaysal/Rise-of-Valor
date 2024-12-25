package game.rise_of_valor.models;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


import java.util.List;

import static game.rise_of_valor.game_engine.MapManager.*;


//import static game.rise_of_valor.data.MapData.MAP1_HEIGHT;
//import static game.rise_of_valor.data.MapData.MAP1_WIDTH;

public class Player extends Character {
    private boolean isMoving = false;










    public Player(Sprite sprite,int inertiaPositionX, int inertiaPositionY) {
        super(inertiaPositionX, inertiaPositionY);



        speed = 200;
        scaleFactor = 10;
        spriteWidth = 600/scaleFactor;
        spriteHeight = 800/scaleFactor;
        spriteAnimationVector = 250;





        this.movementSpriteCount = 7;
        movement = sprite.movement;
//        loadSprites(WALK, movementSpriteCount, movement, playerCharacterId);

        this.idleSpriteCount = 5;
        idle = sprite.idle;
//        loadSprites(IDLE, idleSpriteCount, idle, playerCharacterId);


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
        worldPositionX = Math.max(space+40, Math.min(worldPositionX, MAP1_WIDTH-space-10 - spriteWidth));
        worldPositionY = Math.max(space, Math.min(worldPositionY, MAP1_HEIGHT-space - spriteHeight));

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

            //draw box around character
            gc.setStroke(Color.RED);
//            gc.strokeRect(worldPositionX, worldPositionY, spriteWidth, spriteHeight);


            if (facingLeft) {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, spriteX, spriteY, spriteWidth*scaleFactor, spriteHeight*scaleFactor, -worldPositionX - spriteWidth, worldPositionY, spriteWidth, spriteHeight);
                gc.restore();
            } else {
                gc.drawImage(sprite,spriteX,spriteY,spriteWidth*scaleFactor,spriteHeight*scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
            }


        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
        }
    }

}