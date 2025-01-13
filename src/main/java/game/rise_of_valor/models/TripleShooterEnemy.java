package game.rise_of_valor.models;

import game.rise_of_valor.game_engine.BaseEnemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class TripleShooterEnemy extends BaseEnemy {
    private double maxDistanceFromPlayer = 200;

    public TripleShooterEnemy(Sprite sprite, int worldPositionX, int worldPositionY) {
        super(sprite, worldPositionX, worldPositionY);
        speed = 20;
        fireCooldown = 2.0;
        currentCharacterId = sprite.currentCharacterId;
        moveMode = Sprite.WALK;
    }

    @Override
    public void update(double deltaTime, Player player, List<EnemyBullet> bullets) {
        super.update(deltaTime);
        timeSinceLastFire += deltaTime;

        if (timeSinceLastFire >= fireCooldown) {
            fireTripleBullets(player.worldPositionX, player.worldPositionY, bullets);
            timeSinceLastFire = 0;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
    }

    @Override
    public void move(double deltaTime, Player player, List<BaseEnemy> enemies) {
        double dx = player.worldPositionX - worldPositionX;
        double dy = player.worldPositionY - worldPositionY;
        double distance = Math.hypot(dx, dy);

        if (distance > maxDistanceFromPlayer) {
            double invDistance = 1.0 / distance;
            double normalizedDx = dx * invDistance;
            double normalizedDy = dy * invDistance;

            worldPositionX += normalizedDx * speed * deltaTime;
            worldPositionY += normalizedDy * speed * deltaTime;
        } else {
            // Random movement when within range
            worldPositionX += (Math.random() - 0.5) * speed * deltaTime;
            worldPositionY += (Math.random() - 0.5) * speed * deltaTime;
        }
    }

    private void fireTripleBullets(double targetX, double targetY, List<EnemyBullet> enemyBullets) {
        double baseAngle = Math.atan2(targetY - worldPositionY, targetX - worldPositionX);
        double[] angles = {baseAngle - Math.toRadians(25), baseAngle, baseAngle + Math.toRadians(25)};

        for (double angle : angles) {
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
            enemyBullets.add(new EnemyBullet(worldPositionX, worldPositionY, worldPositionX + dx * 100, worldPositionY + dy * 100, Color.RED, 10));
        }
    }
}
