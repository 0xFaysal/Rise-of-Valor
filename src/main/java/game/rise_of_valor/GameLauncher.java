package game.rise_of_valor;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameLauncher {

    private final int GAME_WIDTH , GAME_HEIGHT;
    private Canvas canvas;
    private GraphicsContext gc;


    public GameLauncher(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        GAME_WIDTH = (int) canvas.getWidth();
        GAME_HEIGHT = (int) canvas.getHeight();
        System.out.println("Game Width: " + GAME_WIDTH + " Game Height: " + GAME_HEIGHT);

    }
}
