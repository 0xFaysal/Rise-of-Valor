package game.rise_of_valor.models;

import game.rise_of_valor.game_engine.BaseEnemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class ShooterEnemy extends BaseEnemy {
    private double maxDistanceFromPlayer = 150;

    public ShooterEnemy(Sprite sprite, int worldPositionX, int worldPositionY) {
        super(sprite, worldPositionX, worldPositionY);
        speed = 30;
        fireCooldown = 3.0;
        currentCharacterId = sprite.currentCharacterId;
        moveMode =  Sprite.WALK;
    }

    @Override
    public void update(double deltaTime, Player player, List<EnemyBullet> enemyBullets) {
        super.update(deltaTime);
        timeSinceLastFire += deltaTime;

        if (timeSinceLastFire >= fireCooldown) {
            enemyBullets.add(new EnemyBullet(worldPositionX, worldPositionY, player.worldPositionX, player.worldPositionY, Color.GREEN, 15));
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
}
