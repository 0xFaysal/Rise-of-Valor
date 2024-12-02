package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;

public class Character {
    int inertiaPositionX;
    int inertiaPositionY;


    int speed = 10;

    public Character(int inertiaPositionX, int inertiaPositionY) {
        this.inertiaPositionX = inertiaPositionX;
        this.inertiaPositionY = inertiaPositionY;

    }

    public void draw(GraphicsContext gc) {
        gc.fillOval(inertiaPositionX,inertiaPositionY, 20, 20);
    }

}