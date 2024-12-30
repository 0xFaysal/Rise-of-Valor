package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
   private double x;
   private double y;

   private double targetX;
   private double targetY;

    double dx,dy;

    private boolean active = true;

    private int bulletSize = 3;

    public Bullet(double startX, double startY, double targetX, double targetY) {
        this.x = startX;
        this.y = startY;
        this.targetX = targetX;
        this.targetY = targetY;

        double angle = Math.atan2(targetY - startY, targetX - startX);
        this.dx = 15 * Math.cos(angle);
        this.dy = 15 * Math.sin(angle);
    }

    public void update(double deltaTime) {
        x += dx*deltaTime *100;
        y += dy*deltaTime *100;
    }

    public boolean isOutOfBounds(int width, int height) {
        return x < 0 || x > width || y < 0 || y > height;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(x - bulletSize, y - bulletSize, 2 * bulletSize, 2 * bulletSize);
    }

//    public boolean isHit(Enemy enemy) {
//        return x > enemy.getX() && x < enemy.getX() + enemy.getWidth() && y > enemy.getY() && y < enemy.getY() + enemy.getHeight();
//    }

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

    public boolean intersects(double[] body){
        return this.x > body[0] && this.x < body[0]+body[2] && this.y > body[1] && this.y < body[1] + body[3];
    }
}
