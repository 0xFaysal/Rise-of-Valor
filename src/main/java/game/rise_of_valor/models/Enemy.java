package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class Enemy extends Character {


    double diagonalSpeed;
    private double previousPositionX;

    public Enemy(int worldPositionX, int worldPositionY) {
        super(worldPositionX, worldPositionY);

        SPRITE_PATH_TEMPLATE = "/game/rise_of_valor/assets/sprites/enemy%d/%s_%d.png";
        scaleFactor = 15;

        speed = 50;
        diagonalSpeed = speed * 0.7071;
        System.out.println("Diagonal Speed: " + diagonalSpeed);
        spriteAnimetionFector = 200;

        movementSpriteCount=7;
        loadSprites(WALK, movementSpriteCount, movement, 1);
        spriteCount=movementSpriteCount-1;
    }

    public void update(double deltaTime) {

        super.update(deltaTime);

    }

    public void moveTowards(double targetX, double targetY,double targetWidth,double targetHeight, double deltaTime) {
        // Calculate the difference in position
        double dx = (targetX + targetWidth/2) - worldPositionX;
        double dy = (targetY+targetHeight/2) - worldPositionY;

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

            // Update position
            worldPositionX += moveX;
            worldPositionY += moveY;

            // Update facing direction
            setFacingLeft(worldPositionX < previousPositionX);

            // Update the previous position
            previousPositionX = worldPositionX;

            // Debugging logs for movement
            System.out.printf(
                    "Enemy Position: (%.2f, %.2f), Target: (%.2f, %.2f), Move: (%.2f, %.2f)%n",
                    worldPositionX, worldPositionY, targetX, targetY, moveX, moveY
            );
        }
    }


    public void draw(GraphicsContext gc) {
        super.draw(gc);
//        System.out.println("Enemy draw");
    }
}
