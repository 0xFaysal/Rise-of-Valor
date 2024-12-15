package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class Enemy extends Character {



    private double previousPositionX;

    public Enemy(int characterId,int worldPositionX, int worldPositionY) {
        super(worldPositionX, worldPositionY);

        SPRITE_PATH_TEMPLATE = "/game/rise_of_valor/assets/sprites/enemy%d/%s_%d.png";
        scaleFactor = 15;

        speed = 50; // Speed of the player in pixels per second
        diagonalSpeed = speed * 0.7071; // Speed of the player in pixels per second when moving diagonally
        spriteAnimetionFector = 200;// Sprite animation speed factor

        if (characterId==4){
            movementSpriteCount=5;
            loadSprites(FLY, movementSpriteCount, movement, characterId);
        } else if (characterId== 2) {
            movementSpriteCount=7;
            loadSprites(WALK, movementSpriteCount, movement, characterId);
            spriteWidth = 650 / scaleFactor;
        } else {
            movementSpriteCount=7;
            loadSprites(WALK, movementSpriteCount, movement, characterId);
        }


        spriteCount=movementSpriteCount-1; // set the sprite count to the last sprite
    }

    public void update(double deltaTime) {

        super.update(deltaTime);

    }

    public void moveTowards(double targetX, double targetY,double targetWidth,double targetHeight, double deltaTime, List<Enemy> enemies) {

        double dx = 0;
        double dy = 0;
        // Calculate the difference in position
        if(targetX<worldPositionX){
            dx = (targetX + targetWidth/2) - worldPositionX;
            dy = (targetY+targetHeight/2) - worldPositionY;
        }else {
            dx = (targetX) - worldPositionX;
            dy = (targetY) - worldPositionY;
        }

        // Calculate the distance to the target
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Move only if the distance is significant
        if (distance > 1) { // Avoid floating-point precision issues
            // Normalize the direction vector
            double normalizedDx = dx / distance;
            double normalizedDy = dy / distance;

            // Calculate movement based on speed and deltaTime
            double moveX = normalizedDx * speed * deltaTime;
            double moveY = normalizedDy * speed * deltaTime;



            // Check distance with other enemies
            for (Enemy enemy : enemies) {
                if (enemy != this) {
                    double distanceToEnemy = Math.sqrt(Math.pow(enemy.worldPositionX - worldPositionX, 2) + Math.pow(enemy.worldPositionY - worldPositionY, 2));
                    if (distanceToEnemy < 25) {
                        double avoidDx = worldPositionX - enemy.worldPositionX;
                        double avoidDy = worldPositionY - enemy.worldPositionY;
                        double avoidDistance = Math.sqrt(avoidDx * avoidDx + avoidDy * avoidDy);
                        if (avoidDistance > 0) {
                            avoidDx /= avoidDistance;
                            avoidDy /= avoidDistance;
                            moveX += avoidDx * speed * deltaTime;
                            moveY += avoidDy * speed * deltaTime;
                        }
                    }
                }
            }

            // Update position
            worldPositionX += moveX;
            worldPositionY += moveY;

            // Update facing direction
            setFacingLeft(worldPositionX < previousPositionX);

            // Update the previous position
            previousPositionX = worldPositionX;


        }
    }


    public void draw(GraphicsContext gc) {
        super.draw(gc);
//        System.out.println("Enemy draw");
    }

    public int getPlayerWidth() {
        return spriteWidth;
    }
    public int getPlayerHeight() {
        return spriteHeight;
    }
}
