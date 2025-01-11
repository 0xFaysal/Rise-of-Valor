package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import static game.rise_of_valor.models.Sprite.FLY;
import static game.rise_of_valor.models.Sprite.WALK;

public class Enemy extends Character {


    private double previousPositionX;


    private int currentCharacterId = 0;

    private PortalEffect portalEffect;
    private double appearanceHeight;
    private double appearanceOpacity;
    private boolean appearanceFinished;


    public Enemy(Sprite sprite, int worldPositionX, int worldPositionY) {
        super(worldPositionX, worldPositionY);

        movement = sprite.movement;
        idle = sprite.idle;


        scaleFactor = 15;


        speed = 50; // Speed of the player in pixels per second
        diagonalSpeed = speed * 0.7071; // Speed of the player in pixels per second when moving diagonally
        spriteAnimationFactor = 200;// Sprite animation speed factor

        this.currentCharacterId = sprite.currentCharacterId;


        //

        this.appearanceHeight = 600;
        this.appearanceOpacity = 0;
        this.appearanceFinished = false;

        // Ensure portalImage is not null
        if (sprite.portal == null) {
            throw new IllegalArgumentException("Portal image cannot be null");
        }

        this.portalEffect = new PortalEffect(worldPositionX + spriteWidth / 2.0, worldPositionY + spriteHeight - 10, sprite.portal);

        if (this.currentCharacterId == 4) {
            speed = 60;
            model = FLY;
            spriteX -= 200;
            spriteWidth = 900 / scaleFactor;
            spriteHeight = 800 / scaleFactor;
            movementSpriteCount = 5;
//            bodyOffsetY = 10;
            bodyOffsetX = 20;
            bodyHeight = 30;
        } else {
            speed = 25;
            model = WALK;
            movementSpriteCount = 7;
            idleSpriteCount = 5;
            bodyWidth = 28;
            if (sprite.currentCharacterId == 2) {
                speed = 15;
                spriteWidth = 650 / scaleFactor;

            }
            if (sprite.currentCharacterId == 3) {
                speed = 40;
                bodyOffsetY = 9;
                bodyOffsetX = 8;
            }
        }


        spriteCount = movementSpriteCount - 1; // set the sprite count to the last sprite
    }

    public void update(double deltaTime) {
        if (portalEffect.isVisible()) {
            portalEffect.update(deltaTime);
            if (portalEffect.isExpired()) {
                portalEffect.update(deltaTime);
                appearanceOpacity += deltaTime * 0.5;
                appearanceHeight -= deltaTime * 500;

                if (appearanceHeight <= 0) {

                    appearanceHeight = 0;
                    appearanceOpacity = 1;
                    appearanceFinished = true;
                    portalEffect.vanish();
//                    portalEffect = null;
                }
            }
        }
            super.update(deltaTime);

    }




    public void moveTowards(double targetX, double targetY, double targetWidth, double targetHeight, double deltaTime, List<Enemy> enemies) {
        if (appearanceFinished) {
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

            bodyX = worldPositionX + bodyOffsetX;
            bodyY = worldPositionY + bodyOffsetY;
            if (facingLeft) {
                switch (currentCharacterId) {
                    case 1, 3 -> bodyX = worldPositionX + bodyOffsetX - 3;
                    case 2 -> bodyX = worldPositionX + bodyOffsetX;
                    case 4 -> bodyX = worldPositionX + bodyOffsetX - 10;
                }
            }
        }
    }


    public void draw(GraphicsContext gc) {
        if (portalEffect.isVisible()) {
            portalEffect.draw(gc);
            if (portalEffect.isExpired() && appearanceFinished) {
                super.draw(gc);
            } else {
                gc.setGlobalAlpha(appearanceOpacity);
                gc.drawImage(movement.get(currentSprite), spriteX, spriteY - appearanceHeight, spriteWidth * scaleFactor, spriteHeight * scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
                gc.setGlobalAlpha(1);
            }

        } else if (appearanceFinished) {
            super.draw(gc);
        }
    }

    public double[] getBody() {
        return new double[]{bodyX, bodyY, bodyWidth, bodyHeight};
    }

    public int getCurrentCharacterId() {
        return currentCharacterId;
    }
}