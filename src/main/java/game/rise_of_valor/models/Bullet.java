package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
    private double x;
    private double y;

    private final double dx;
    private final double dy;

    private boolean active = true;

    private final int bulletSize = 3; // Bullet radius
    private final double speed = 1000; // Speed of the bullet

    public Bullet(double startX, double startY, double targetX, double targetY) {
        this.x = startX;
        this.y = startY;

        // Calculate direction vector and normalize
        double angle = Math.atan2(targetY+12 - startY, targetX+12 - startX);
        this.dx = Math.cos(angle);
        this.dy = Math.sin(angle);
    }

    public void update(double deltaTime) {
        // Update position based on direction and speed
        x += dx * speed * deltaTime;
        y += dy * speed * deltaTime;
    }

    public boolean isOutOfBounds(int width, int height) {
        // Check if the bullet is out of map bounds
        return x + bulletSize < 0 || x - bulletSize > width || y + bulletSize < 0 || y - bulletSize > height;
    }

    public void draw(GraphicsContext gc) {
        // Draw bullet as a small circle
        gc.setFill(Color.BLACK);
        gc.fillOval(x - bulletSize, y - bulletSize, 2 * bulletSize, 2 * bulletSize);
    }



    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }



    /**
     * Check if the bullet intersects with the enemy's body
     * @param body The enemy's body as an array of doubles [x, y, width, height]
     * @return true if the bullet intersects with the enemy's body, false otherwise
     */
    public boolean intersects(double[] body) {
        // Bullet collision detection with the enemy's body
        double bulletLeft = x - bulletSize;
        double bulletRight = x + bulletSize;
        double bulletTop = y - bulletSize;
        double bulletBottom = y + bulletSize;

        double bodyLeft = body[0];
        double bodyRight = body[0] + body[2];
        double bodyTop = body[1];
        double bodyBottom = body[1] + body[3];

        return bulletRight > bodyLeft && bulletLeft < bodyRight &&
                bulletBottom > bodyTop && bulletTop < bodyBottom;
    }


}
