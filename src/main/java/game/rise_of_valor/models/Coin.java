package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static game.rise_of_valor.shareData.DataManager.coinIcon;

public class Coin {
    public double worldPositionX;
    public double worldPositionY;
    private boolean isActive;
    private double speed = 10;

    private boolean isMoving = false;

    public Coin(double worldPositionX, double worldPositionY) {
        System.out.println("Coin created");
        this.worldPositionX = worldPositionX;
        this.worldPositionY = worldPositionY;
        this.isActive = true;

    }

    public void update(double deltaTime, Player player) {
        double distance = Math.hypot(player.getBody()[0]+player.getBody()[2] /2 - worldPositionX, player.getBody()[1]+player.getBody()[3] /2 - worldPositionY);
        if (distance < 100 || isMoving) {
            isActive = true;
            double dx = player.getBody()[0]+player.getBody()[2] /2 - worldPositionX;
            double dy = player.getBody()[1]+player.getBody()[3] /2 - worldPositionY;
            double invDistance = 1.0 / distance;
            worldPositionX += dx * invDistance * 100 * deltaTime * speed;
            worldPositionY += dy * invDistance * 100 * deltaTime * speed;

            if (distance < 10) {
                isActive = false;
                player.addCoin();
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void draw(GraphicsContext gc) {
       gc.drawImage(coinIcon, worldPositionX, worldPositionY, 18, 18);
    }
}