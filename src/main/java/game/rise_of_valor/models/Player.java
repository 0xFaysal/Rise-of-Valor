package game.rise_of_valor.models;


import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static game.rise_of_valor.data.MapData.MAP1_PLAYGROUND_HEIGHT;
import static game.rise_of_valor.data.MapData.MAP1_PLAYGROUND_WIDTH;

public class Player extends Character {
    private boolean isMoving = false;
    private static final String SPRITE_PATH_TEMPLATE = "/game/rise_of_valor/assets/sprites/player%d/%s_%d.png";
    private static final String WALK = "walk";
    private static final String IDLE = "idle";

    int playerCharacterId = 1;
    private final RadialGradient shadowGradient;

    //sprite size

    int spriteWidth;
    int spriteHeight;
    int scaleFactor;


    // Camera
    private final int cameraX;
    private final int cameraY;
    private final int cameraWidth;
    private final int cameraHeight;


    public Player(int cameraWidth,int cameraHeight) {

        super(MAP1_PLAYGROUND_WIDTH/2, MAP1_PLAYGROUND_HEIGHT/2);
        scaleFactor = 8;
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

        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;

        cameraX = (cameraWidth/2 )-(spriteWidth/2);
        cameraY = cameraHeight/2 - (spriteHeight/2);
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
                worldY -= (int) (speed * deltaTime);
                isMoving = true;
            }
            if (key == KeyCode.S || key == KeyCode.DOWN) {
                worldY += (int) (speed * deltaTime);
                isMoving = true;
            }
            if (key == KeyCode.A || key == KeyCode.LEFT) {
                worldX -= (int) (speed * deltaTime);
                setFacingLeft(true);
                isMoving = true;
            }
            if (key == KeyCode.D || key == KeyCode.RIGHT) {
                worldX += (int) (speed * deltaTime);
                setFacingLeft(false);
                isMoving = true;
            }
        }

        // Update spriteCount based on movement state
        spriteCount = isMoving ? movementSpriteCount : idleSpriteCount;

        // Update animation
        super.update(deltaTime);
    }

    public int getCameraX() {
        return cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }

    public int getCameraWidth() {
        return cameraWidth;
    }

    public int getCameraHeight() {
        return cameraHeight;
    }
    //    public void draw(GraphicsContext gc, Camera camera) {
//        List<Image> sprites = isMoving ? movement : idle;
//        if (currentSprite < sprites.size()) {
//            Image sprite = sprites.get(currentSprite);
//            double spriteWidth = sprite.getWidth();
//            double spriteHeight = sprite.getHeight();
//
//
//
//            int shadowWidth = 40;
//            int shadowXOffset = 35;
//
//            gc.setFill(shadowGradient);
//            gc.fillOval(worldX + shadowWidth + shadowXOffset - camera.getCameraX(), worldY + scaledTileSize - 30 - camera.getCameraY(), shadowWidth, 10);
//
//            if (facingLeft) {
//                gc.save();
//                gc.scale(-1, 1);
//                gc.drawImage(sprite, -worldX - scaledTileSize + camera.getCameraX(), worldY - camera.getCameraY(), scaledTileSize, scaledTileSize);
//                gc.restore();
//            } else {
//                gc.drawImage(sprite, 0, 0, spriteWidth, spriteHeight, worldX - camera.getCameraX(), worldY - camera.getCameraY(), scaledTileSize, scaledTileSize);
//            }
//
////            System.out.println("Player X: " + initialPositionX + " Player Y: " + initialPositionY);
////            System.out.println("Camera X: " + camera.getX() + " Camera Y: " + camera.getY());
////            System.out.println("Player X - Camera X: " + (initialPositionX - camera.getX()) + " Player Y - Camera Y: " + (initialPositionY - camera.getY()));
//
//
//            double drawX = worldX - camera.getCameraX();
//            double drawY = worldY - camera.getCameraY();
//            // Draw border around the sprite
//            gc.setStroke(Color.RED); // Set the border color
//            gc.setLineWidth(2); // Set the border width
//            gc.strokeRect(drawX, drawY, scaledTileSize, scaledTileSize);
//
//            // draw a circle in the initial position of the player
//            gc.setFill(Color.TOMATO);
//            gc.fillOval(worldX, worldY, 5, 5);
//
//
//            playerBoxX = worldX - camera.getCameraX() +scaledTileSize/2 -28;
//            playerBoxY = worldY - camera.getCameraY()+scaledTileSize/2;
//
//            // draw a circle in the initial position of the player
//            gc.setFill(Color.BEIGE);
//            gc.fillOval(playerBoxX,playerBoxY, 5, 5);
//            // draw a rectangle around the player
//            gc.setStroke(Color.GREEN); // Set the border color
//            gc.setLineWidth(2); // Set the border width
//            gc.strokeRect(playerBoxX,playerBoxY, playerBoxWidth, playerBoxHeight);
//        } else {
//            System.out.println("No sprites loaded or invalid sprite index.");
//        }
//    }



//    @Override
    public void draw(GraphicsContext gc, double interpolation) {
        List<Image> sprites = isMoving ? movement : idle;
        if (currentSprite < sprites.size()) {
            Image sprite = sprites.get(currentSprite);
//            double width = sprite.getWidth();
//            double height = sprite.getHeight();
//            double scaledTileSize = tileSize * tileScale;

            int spriteX = 700;
            int spriteY = 1030;


            // Calculate shadow width using a sine wave function for smooth animation
            double time = System.currentTimeMillis() / 1000.0;
            double shadowWidth = 40 + 13 * Math.sin(time * 1.5 * Math.PI); // Adjust amplitude and frequency as needed
            double shadowXOffset = (spriteWidth - shadowWidth) / 2;

            // Calculate shadow position based on player's position
            double shadowX = cameraX + shadowXOffset;
            double shadowY = cameraY + spriteHeight - 15;

            // Draw realistic shadow under the character
            gc.setFill(shadowGradient);
            gc.fillOval(shadowX, shadowY, shadowWidth, 15);


            //draw a circle in the initial position of the player
            gc.setFill(Color.TOMATO);
            gc.fillOval(cameraX, cameraY, 5, 5);



            gc.setStroke(Color.YELLOW); // Set the border color
            gc.setLineWidth(2); // Set the border width
            gc.strokeRect(cameraX, cameraY, spriteWidth, spriteHeight);




            if (facingLeft) {
                gc.save();
                gc.scale(-1, 1);
                gc.drawImage(sprite, spriteX, spriteY, spriteWidth*scaleFactor, spriteHeight*scaleFactor, -cameraX - spriteWidth, cameraY, spriteWidth, spriteHeight);
                gc.restore();
            } else {
                gc.drawImage(sprite,spriteX,spriteY,spriteWidth*scaleFactor,spriteHeight*scaleFactor, cameraX, cameraY, spriteWidth, spriteHeight);
            }
        } else {
            System.out.println("No sprites loaded or invalid sprite index.");
        }
    }
}