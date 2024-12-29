package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import static game.rise_of_valor.models.Sprite.FLY;
import static game.rise_of_valor.models.Sprite.WALK;

public class Enemy2 extends Character {

    private double previousPositionX;

    public Enemy2(Sprite sprite, int worldPositionX, int worldPositionY) {
        super(worldPositionX, worldPositionY);

        movement = sprite.movement;
        idle = sprite.idle;

        scaleFactor = 15;
        spriteAnimationVector = 200; // Sprite animation speed factor

        // Initialize enemy attributes based on sprite's character ID
        if (sprite.currentCharacterId == 4) {
            initFlyingEnemy();
        } else {
            initWalkingEnemy(sprite);
        }

        spriteCount = movementSpriteCount - 1; // Set the sprite count to the last sprite
    }

    private void initFlyingEnemy() {
        speed = 105;
        model = FLY;
        spriteX -= 200;
        spriteWidth = 900 / scaleFactor;
        spriteHeight = 800 / scaleFactor;
        movementSpriteCount = 5;
    }

    private void initWalkingEnemy(Sprite sprite) {
        speed = 65;
        model = WALK;
        movementSpriteCount = 7;
        idleSpriteCount = 5;

        if (sprite.currentCharacterId == 2) {
            speed = 50;
            spriteWidth = 650 / scaleFactor;
        } else if (sprite.currentCharacterId == 3) {
            speed = 80;
        }
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    public void moveTowards(double targetX, double targetY, double targetWidth, double targetHeight, double deltaTime, List<Enemy2> enemies) {
        double dx = targetX - worldPositionX;
        double dy = targetY - worldPositionY;

        // Calculate squared distance to avoid Math.hypot for performance
        double distanceSquared = dx * dx + dy * dy;

        // Move only if the distance is significant (use squared distance to avoid square root)
        if (distanceSquared > 1) {
            // Normalize the direction vector
            double distance = Math.sqrt(distanceSquared); // Only calculate square root once
            double invDistance = 1.0 / distance;
            double normalizedDx = dx * invDistance;
            double normalizedDy = dy * invDistance;

            // Calculate movement based on speed and deltaTime
            double moveX = normalizedDx * speed * deltaTime;
            double moveY = normalizedDy * speed * deltaTime;

            isMoving = true;

            // Check distance with other enemies
            avoidCollisionsWithEnemies(enemies, moveX, moveY, deltaTime);

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
        if (distanceSquared <= 1 || !isMoving) {
            currentSprite = 0; // Assuming the first sprite in the idle list is the idle sprite
            isMoving = false;
        }

        spriteCount = isMoving ? movementSpriteCount - 1 : (idleSpriteCount > 0 ? idleSpriteCount - 1 : movementSpriteCount - 1);

        // Ensure currentSprite is within valid range
        if (currentSprite < 0 || currentSprite >= spriteCount) {
            currentSprite = 0;
        }
    }

    private void avoidCollisionsWithEnemies(List<Enemy2> enemies, double moveX, double moveY, double deltaTime) {
        // Check for nearby enemies to avoid overlap
        for (Enemy2 enemy : enemies) {
            if (enemy != this) {
                double enemyDx = enemy.worldPositionX - worldPositionX;
                double enemyDy = enemy.worldPositionY - worldPositionY;
                double distanceToEnemySquared = enemyDx * enemyDx + enemyDy * enemyDy;

                if (distanceToEnemySquared < 35 * 35) { // Compare squared distances for performance
                    double distanceToEnemy = Math.sqrt(distanceToEnemySquared);
                    double dampingFactor = 1 - (distanceToEnemy / 35);
                    if (dampingFactor > 1) {
                        // Avoid collision by adjusting movement
                        double invDis = 1.0 / distanceToEnemy;
                        double avoidDx = enemyDx * invDis;
                        double avoidDy = enemyDy * invDis;
                        moveX -= avoidDx * speed * deltaTime * dampingFactor;
                        moveY -= avoidDy * speed * deltaTime * dampingFactor;
                    }
                }
            }
        }
    }

    public void draw(GraphicsContext gc) {
        super.draw(gc);
    }
}
