package game.rise_of_valor.models;

import game.rise_of_valor.effects.PortalEffect;
import game.rise_of_valor.game_engine.MapManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

import static game.rise_of_valor.models.Sprite.FLY;
import static game.rise_of_valor.models.Sprite.WALK;

public class Enemy extends Character {


    private final double fireCooldown;
    private double previousPositionX; // Previous position of the character on the x-axis for checking the facing direction

    protected double diagonalSpeed; // Speed of the character in pixels per second when moving diagonally
    double lastTime = 0;
    double targetX = 0;
    double targetY = 0;

    private int currentCharacterId = 0; // Current character ID

    private PortalEffect portalEffect; // Portal effect for the enemy appearance
    private double appearanceHeight; // Height of the enemy appearance effect for the portal
    private double appearanceOpacity; // Opacity of the enemy appearance effect for the portal
    private boolean appearanceFinished; // Flag to indicate if the enemy appearance effect has finished


    public Enemy(Sprite sprite, int worldPositionX, int worldPositionY) {
        super(worldPositionX, worldPositionY);

        movement = sprite.movement;
        idle = sprite.idle;

        fireCooldown = 2.0;


        speed = 50; // Speed of the player in pixels per second default 100
        spriteAnimationFactor = 200;// Sprite animation speed factor

        this.currentCharacterId = sprite.currentCharacterId; // Set the current character ID


        this.appearanceHeight = 600; // Set the appearance height for the portal effect
        this.appearanceOpacity = 0; // Set the appearance opacity for the portal effect
        this.appearanceFinished = false; // Set the appearance finished flag to false

        // Ensure portalImage is not null
        if (sprite.portal == null) {
            throw new IllegalArgumentException("Portal image cannot be null");
        }


        // Set the sprite properties based on the current character ID
        if (this.currentCharacterId == 4) {
//            System.out.println("spriteWidth: "+spriteWidth+" spriteHeight: "+spriteHeight);
            speed = 90;
            moveMode = FLY;
//            spriteX -= 200;
//            spriteWidth = 900 / scaleFactor;
//            spriteHeight = 800 / scaleFactor;
            movementSpriteCount = 5;
            bodyOffsetX = 20;
            bodyHeight = 30;
        } else {
            speed = 85;
            moveMode = WALK;
            movementSpriteCount = 7;
            idleSpriteCount = 5;
            bodyWidth = 28;
            if (sprite.currentCharacterId == 2) {
                speed = 35;
//                spriteWidth = 650 / scaleFactor;
            }
            if (sprite.currentCharacterId == 3) {
                speed = 60;
                bodyOffsetY = 9;
                bodyOffsetX = 8;
            }
        }
        diagonalSpeed = speed * 0.7071; // Speed of the player in pixels per second when moving diagonally

        if (!movement.isEmpty()) {
            spriteWidth = (movement.getFirst().getWidth() / scaleFactor);
            spriteHeight = (movement.getFirst().getHeight() / scaleFactor);
        }
        this.portalEffect = new PortalEffect(worldPositionX + spriteWidth / 2.0, worldPositionY + spriteHeight - 10, sprite.portal);


        spriteCount = movementSpriteCount - 1; // set the sprite count to the last sprite
    }

    public void update(double deltaTime) {
        super.update(deltaTime);

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


        // Update the portal effect
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
                }
            }
        }

    }

    double timeSinceLastFire;

    public void attack(double deltaTime, Player player, List<EnemyBullet> enemyBullets) {
        if (appearanceFinished) {
            timeSinceLastFire += deltaTime;
            if (timeSinceLastFire >= fireCooldown) {
                double distanceToPlayer = Math.hypot(player.worldPositionX - worldPositionX, player.worldPositionY - worldPositionY);
                if (currentCharacterId == 1 || currentCharacterId == 2) {
                    if (distanceToPlayer <= 100) { // Attack range for characterId 1 and 2
                        player.takeDamage(1); // Example damage value
                    }
                }
                if (currentCharacterId == 2 || currentCharacterId == 3) {

                    if (distanceToPlayer <= 600) { // Firing range for characterId 2 and 3
                        enemyBullets.add(new EnemyBullet(worldPositionX, worldPositionY, player.worldPositionX, player.worldPositionY, Color.color(1, 0.1, 0.5)));
                    }

                }
                timeSinceLastFire = 0;
            }
        }
    }


    public void moveRandomly(double deltaTime, MapManager mapManager, List<Enemy> enemies) {
        if (appearanceFinished) {

            lastTime += deltaTime;
            // Move randomly

//            System.out.println("Last time: " + lastTime);
            if (lastTime > 3) {
                Random random = new Random();
//                System.out.println("New target");
                targetX = random.nextDouble() * (mapManager.getMapWidth() - spriteWidth);
                targetY = random.nextDouble() * (mapManager.getMapHeight() - spriteHeight);
//                System.out.println("Target X: " + targetX + " Target Y: " + targetY);
                lastTime = 0;
            }

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
            if (!isMoving) {
                currentSprite = 0; // Assuming the first sprite in the idle list is the idle sprite
                isMoving = false;
            }
        }
    }


    public void moveTowards(double targetX, double targetY, double deltaTime, List<Enemy> enemies) {
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
        }
    }


    public void draw(GraphicsContext gc) {
        if (portalEffect.isVisible()) {
            portalEffect.draw(gc);
            if (portalEffect.isExpired() && appearanceFinished) {
                super.draw(gc);
            } else {
                gc.setGlobalAlpha(appearanceOpacity);
                gc.drawImage(movement.get(currentSprite), 0, 0 - appearanceHeight, spriteWidth * scaleFactor, spriteHeight * scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
                gc.setGlobalAlpha(1);
            }

        } else if (appearanceFinished) {
            super.draw(gc);
        }
    }


    public int getCurrentCharacterId() {
        return currentCharacterId;
    }

//    public void reset(Sprite sprite, int x, int y) {
//        this.worldPositionX = x;
//        this.worldPositionY = y;
//        this.previousPositionX = x;
//        this.movement = sprite.movement;
//        this.idle = sprite.idle;
//        this.currentCharacterId = sprite.currentCharacterId;
//        this.appearanceHeight = 600;
//        this.appearanceOpacity = 0;
//        this.appearanceFinished = false;
//        this.portalEffect = new PortalEffect(x + spriteWidth / 2.0, y + spriteHeight - 10, sprite.portal);
//
//        if (this.currentCharacterId == 4) {
//            this.speed = 60;
//            this.moveMode = FLY;
////            this.spriteX -= 200;
//            this.spriteWidth = 900 / scaleFactor;
//            this.spriteHeight = 800 / scaleFactor;
//            this.movementSpriteCount = 5;
//            this.bodyOffsetX = 20;
//            this.bodyHeight = 30;
//        } else {
//            this.speed = 25;
//            this.moveMode = WALK;
//            this.movementSpriteCount = 7;
//            this.idleSpriteCount = 5;
//            this.bodyWidth = 28;
//            if (sprite.currentCharacterId == 2) {
//                this.speed = 15;
//                this.spriteWidth = 650 / scaleFactor;
//            }
//            if (sprite.currentCharacterId == 3) {
//                this.speed = 40;
//                this.bodyOffsetY = 9;
//                this.bodyOffsetX = 8;
//            }
//        }
//        this.spriteCount = this.movementSpriteCount - 1;
//        this.isMoving = false;
//        this.currentSprite = 0;
//    }
}