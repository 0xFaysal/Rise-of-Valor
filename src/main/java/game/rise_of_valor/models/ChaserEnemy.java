package game.rise_of_valor.models;

import game.rise_of_valor.game_engine.BaseEnemy;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class ChaserEnemy extends BaseEnemy {
    private double damageRange = 10;

    public ChaserEnemy(Sprite sprite, int worldPositionX, int worldPositionY) {
        super(sprite, worldPositionX, worldPositionY);
        speed = sprite.currentCharacterId == 4 ? 60 : 50;
        moveMode = sprite.currentCharacterId == 4 ? Sprite.FLY : Sprite.WALK;
        currentCharacterId = sprite.currentCharacterId;
    }

    @Override
    public void update(double deltaTime, Player player, List<EnemyBullet> bullets) {
        super.update(deltaTime);
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

        if (distance > 1) {
            double invDistance = 1.0 / distance;
            double normalizedDx = dx * invDistance;
            double normalizedDy = dy * invDistance;

            worldPositionX += normalizedDx * speed * deltaTime;
            worldPositionY += normalizedDy * speed * deltaTime;

            setFacingLeft(worldPositionX < player.worldPositionX);
        }

        if (distance <= damageRange) {
            player.takeDamage(10);
        }
    }
}
