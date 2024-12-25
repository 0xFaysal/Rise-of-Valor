package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import static game.rise_of_valor.models.Sprite.FLY;
import static game.rise_of_valor.models.Sprite.WALK;

public class Enemy extends Character {



    private double previousPositionX;


    public Enemy(Sprite sprite ,int worldPositionX, int worldPositionY) {
        super(worldPositionX, worldPositionY);
        
        movement = sprite.movement;
        idle = sprite.idle;
        


        scaleFactor = 15;



        speed = 50; // Speed of the player in pixels per second
        diagonalSpeed = speed * 0.7071; // Speed of the player in pixels per second when moving diagonally
        spriteAnimationVector = 200;// Sprite animation speed factor


        if (sprite.currentCharacterId == 4) {
            speed = 105;
            model = FLY;
            spriteX -= 200;
            spriteWidth = 900 / scaleFactor;
            spriteHeight = 800 / scaleFactor;
            movementSpriteCount = 5;
        } else {
            speed = 65;
            model = WALK;
            movementSpriteCount = 7;
            idleSpriteCount = 5;
            if (sprite.currentCharacterId == 2) {
                speed = 50;
                spriteWidth = 650 / scaleFactor;
            }
            if (sprite.currentCharacterId == 3){
                speed=80;
            }
        }
//        if (sprite.currentCharacterId==4) {
//            model = FLY;
//            spriteX-=200;
//            spriteWidth = 900 / scaleFactor;
//            spriteHeight = 800 / scaleFactor;
//        }
//        else model=WALK;
//        if (sprite.currentCharacterId==4){
//            movementSpriteCount=5;
//        } else if (sprite.currentCharacterId== 2) {
//            movementSpriteCount=7;
//            this.idleSpriteCount = 5;
//            spriteWidth = 650 / scaleFactor;
//        } else {
//            movementSpriteCount=7;
//            this.idleSpriteCount = 5;
//
//        }


        spriteCount=movementSpriteCount-1; // set the sprite count to the last sprite
    }

    public void update(double deltaTime) {

        super.update(deltaTime);

    }


    public void moveTowards(double targetX, double targetY, double targetWidth, double targetHeight, double deltaTime, List<Enemy> enemies) {
        double dx = targetX - worldPositionX;
        double dy = targetY - worldPositionY;

        // Calculate the distance to the target
        double distance = Math.hypot(dx, dy);

        // Move only if the distance is significant
        if (distance > 1) { // Avoid floating-point precision issues
            // Normalize the direction vector
            double invDistance = 1.0 / distance;
            double normalizedDx = dx * invDistance;
            double normalizedDy = dy * invDistance;

            // Calculate movement based on speed and deltaTime
            double moveX = normalizedDx * speed * deltaTime;
            double moveY = normalizedDy * speed * deltaTime;

            isMoving = true;

            // Check distance with other enemies
            for (Enemy enemy : enemies) {
                if (enemy != this) {
                    double enemyDx = enemy.worldPositionX - worldPositionX;
                    double enemyDy = enemy.worldPositionY - worldPositionY;
//                    double distanceToEnemy = Math.hypot(enemyDx, enemyDy);
                    double distanceToEnemy = Math.sqrt(enemyDx * enemyDx + enemyDy * enemyDy);

                    if (distanceToEnemy < 35) {
                        double invDis = 1.0 / distanceToEnemy;
                        double dampingFactor = 1 - (distanceToEnemy / 35);
                        if (dampingFactor > 0) {
                            double avoidDx = enemyDx * invDis;
                            double avoidDy = enemyDy * invDis;
                            moveX -= avoidDx * speed * deltaTime * dampingFactor;
                            moveY -= avoidDy * speed * deltaTime * dampingFactor;
                        }
                    }
//                    if (distanceToEnemy < 35) {
//                        double avoidDistance = 1.0 / distanceToEnemy;
//                        double avoidDx = enemyDx * avoidDistance;
//                        double avoidDy = enemyDy * avoidDistance;
//                        double dampingFactor = Math.max(0, 1 - (distanceToEnemy / 35));
//                        moveX -= avoidDx * speed * deltaTime * dampingFactor;
//                        moveY -= avoidDy * speed * deltaTime * dampingFactor;
//                    }
                }
            }

            // Update position
            worldPositionX += moveX;
            worldPositionY += moveY;

            // Update facing direction
            setFacingLeft(worldPositionX < previousPositionX);

            // Update the previous position
            previousPositionX = worldPositionX;
        } else {
            isMoving = false;
        }

        // Set to idle sprite if the enemy is near the player or not moving
        if (distance <= 1 || !isMoving) {
            currentSprite = 0; // Assuming the first sprite in the idle list is the idle sprite
            isMoving = false;
        }

        spriteCount = isMoving ? movementSpriteCount - 1 : (idleSpriteCount > 0 ? idleSpriteCount - 1 : movementSpriteCount - 1);

        // Ensure currentSprite is within valid range
        if (currentSprite < 0 || currentSprite >= spriteCount) {
            currentSprite = 0;
        }
    }




//    public void moveTowards(double targetX, double targetY, double targetWidth, double targetHeight, double deltaTime, List<Enemy> enemies) {
//        double dx = targetX - worldPositionX;
//        double dy = targetY - worldPositionY;
//
//        // Calculate the distance to the target
//        double distance = Math.sqrt(dx * dx + dy * dy);
//
//        // Move only if the distance is significant
//        if (distance > 1) { // Avoid floating-point precision issues
//            // Normalize the direction vector
//            double normalizedDx = dx / distance;
//            double normalizedDy = dy / distance;
//
//            // Calculate movement based on speed and deltaTime
//            double moveX = normalizedDx * speed * deltaTime;
//            double moveY = normalizedDy * speed * deltaTime;
//
//            isMoving = true;
//
//            // Check distance with other enemies
//            for (Enemy enemy : enemies) {
//                if (enemy != this) {
//                    double distanceToEnemy = Math.sqrt(Math.pow(enemy.worldPositionX - worldPositionX, 2) + Math.pow(enemy.worldPositionY - worldPositionY, 2));
//                    if (distanceToEnemy < 35) {
//                        double avoidDx = worldPositionX - enemy.worldPositionX;
//                        double avoidDy = worldPositionY - enemy.worldPositionY;
//                        double avoidDistance = Math.sqrt(avoidDx * avoidDx + avoidDy * avoidDy);
//                        if (avoidDistance > 0) {
//                            avoidDx /= avoidDistance;
//                            avoidDy /= avoidDistance;
//                            double dampingFactor = Math.max(0, 1 - (distanceToEnemy / 35));
//                            moveX += avoidDx * speed * deltaTime * dampingFactor;
//                            moveY += avoidDy * speed * deltaTime * dampingFactor;
//                        }
//                    }
//                }
//            }
//
//            // Update position
//            worldPositionX += moveX;
//            worldPositionY += moveY;
//
//            // Update facing direction
//            setFacingLeft(worldPositionX < previousPositionX);
//
//            // Update the previous position
//            previousPositionX = worldPositionX;
//        } else {
//            isMoving = false;
//        }
//
//        // Set to idle sprite if the enemy is near the player or not moving
//        if (distance <= 1 || !isMoving) {
//            currentSprite = 0; // Assuming the first sprite in the idle list is the idle sprite
//            isMoving = false;
//        }
//
//        spriteCount = isMoving ? movementSpriteCount - 1 : (idleSpriteCount > 0 ? idleSpriteCount - 1 : movementSpriteCount - 1);
//
//        // Ensure currentSprite is within valid range
//        if (currentSprite < 0 || currentSprite >= spriteCount) {
//            currentSprite = 0;
//        }
//    }



//    public void moveTowards(double targetX, double targetY,double targetWidth,double targetHeight, double deltaTime, List<Enemy> enemies) {
//
//        double dx = 0;
//        double dy = 0;
//        // Calculate the difference in position
//        if(targetX<worldPositionX){
//            dx = (targetX + targetWidth/2) - worldPositionX;
//            dy = (targetY+targetHeight/2) - worldPositionY;
//        }else {
//            dx = (targetX) - worldPositionX;
//            dy = (targetY) - worldPositionY;
//        }
//
//        // Calculate the distance to the target
//        double distance = Math.sqrt(dx * dx + dy * dy);
//
//        // Move only if the distance is significant
//        if (distance > 1) { // Avoid floating-point precision issues
//            // Normalize the direction vector
//            double normalizedDx = dx / distance;
//            double normalizedDy = dy / distance;
//
//            // Calculate movement based on speed and deltaTime
//            double moveX = normalizedDx * speed * deltaTime;
//            double moveY = normalizedDy * speed * deltaTime;
//
//            isMoving = true;
//            // Check distance with other enemies
//            for (Enemy enemy : enemies) {
//                if (enemy != this) {
//                    double distanceToEnemy = Math.sqrt(Math.pow(enemy.worldPositionX - worldPositionX, 2) + Math.pow(enemy.worldPositionY - worldPositionY, 2));
//                    if (distanceToEnemy < 35) {
//                        double avoidDx = worldPositionX - enemy.worldPositionX;
//                        double avoidDy = worldPositionY - enemy.worldPositionY;
//                        double avoidDistance = Math.sqrt(avoidDx * avoidDx + avoidDy * avoidDy);
//                        if (avoidDistance > 0) {
//                            avoidDx /= avoidDistance;
//                            avoidDy /= avoidDistance;
//                            double dampingFactor = Math.max(0, 1 - (distanceToEnemy / 35));
//                            moveX += avoidDx * speed * deltaTime * dampingFactor;
//                            moveY += avoidDy * speed * deltaTime * dampingFactor;
//                        }
//                    }
//                }
//            }
//
//
//
//            // Update position
//            worldPositionX += moveX;
//            worldPositionY += moveY;
//
//            // Update facing direction
//            setFacingLeft(worldPositionX < previousPositionX);
//
//            // Update the previous position
//            previousPositionX = worldPositionX;
//
//
//        }else {
//            isMoving = false;
//        }
//        // Set to idle sprite if the enemy is near the player or not moving
//        if (distance <= 1 || !isMoving) {
//            currentSprite = 0; // Assuming the first sprite in the idle list is the idle sprite
//            isMoving = false;
//        }
//
//        spriteCount = isMoving ? movementSpriteCount - 1 : (idleSpriteCount > 0 ? idleSpriteCount - 1 : movementSpriteCount - 1);
//
//        // Ensure currentSprite is within valid range
//        if (currentSprite < 0 || currentSprite >= spriteCount) {
//            currentSprite = 0;
//        }
//    }


    public void draw(GraphicsContext gc) {
        super.draw(gc);
//        System.out.println("Enemy draw");
    }


}




//            // Check distance with other enemies
//            for (Enemy enemy : enemies) {
//                if (enemy != this) {
//                    double distanceToEnemy = Math.sqrt(Math.pow(enemy.worldPositionX - worldPositionX, 2) + Math.pow(enemy.worldPositionY - worldPositionY, 2));
//                    if (distanceToEnemy < 25) {
//                        double avoidDx = worldPositionX - enemy.worldPositionX;
//                        double avoidDy = worldPositionY - enemy.worldPositionY;
//                        double avoidDistance = Math.sqrt(avoidDx * avoidDx + avoidDy * avoidDy);
//                        if (avoidDistance > 0) {
//                            avoidDx /= avoidDistance;
//                            avoidDy /= avoidDistance;
//                            moveX += avoidDx * speed * deltaTime;
//                            moveY += avoidDy * speed * deltaTime;
//                        }
//                    }
//                }
//            }