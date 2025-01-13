package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EnemyBullet {
    private double x, y; // Current position
    private double dx, dy; // Movement direction
    private boolean active = true; // Is the bullet active
    private final int bulletSize = 5; // Bullet size
    private final Color color; // Bullet color (e.g., green or red)
    private final int damage; // Damage caused by the bullet

    public EnemyBullet(double startX, double startY, double targetX, double targetY, Color color, int damage) {
        this.x = startX;
        this.y = startY;
        this.color = color;
        this.damage = damage;

        // Calculate direction
        double angle = Math.atan2(targetY - startY, targetX - startX);
        this.dx = 10 * Math.cos(angle); // Bullet speed
        this.dy = 10 * Math.sin(angle); // Bullet speed
    }

    public void update(double deltaTime) {
        x += dx * deltaTime * 100;
        y += dy * deltaTime * 100;
    }

    public boolean isOutOfBounds(int width, int height) {
        return x < 0 || x > width || y < 0 || y > height;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - bulletSize, y - bulletSize, 2 * bulletSize, 2 * bulletSize);
    }

    public boolean intersects(double[] playerBounds) {
        return x > playerBounds[0] && x < playerBounds[0] + playerBounds[2] &&
                y > playerBounds[1] && y < playerBounds[1] + playerBounds[3];
    }

    public int getDamage() {
        return damage;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
