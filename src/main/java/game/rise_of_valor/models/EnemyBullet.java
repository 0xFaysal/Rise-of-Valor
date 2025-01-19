package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class EnemyBullet {
    private double x;
    private double y;
    private final double dx;
    private final double dy;
    private boolean active = true;
    private final int bulletSize = 6; // Bullet radius
    private final double speed = 190; // Speed of the bullet
    private final RadialGradient radialGradient ;// Color of the bullet

    public EnemyBullet(double startX, double startY, double targetX, double targetY, String color) {
        this.x = startX;
        this.y = startY;
        if (color.equals("red")) {
            this.radialGradient = new RadialGradient(
                    0, 0.1, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.ORANGE),
                    new Stop(1, Color.FIREBRICK)
            );
        } else {
            this.radialGradient = new RadialGradient(
                    0, 0.1, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.ORANGERED),
                    new Stop(1, Color.ORANGE)
            );
        }

        // Calculate direction vector and normalize
        double angle = Math.atan2(targetY - startY, targetX - startX);
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
        gc.setFill(radialGradient);
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