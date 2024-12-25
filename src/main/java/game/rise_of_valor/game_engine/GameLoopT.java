
package game.rise_of_valor.game_engine;

import game.rise_of_valor.game_engine.GameWorld;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameLoopT extends Thread {

    private final GraphicsContext gc;
    private final GameWorld gameWorld;

    // Constants for fixed time step game loop
    private static final int TARGET_FPS = 60; // Target frames per second
    private static final double TIME_PER_FRAME = 1.0 / TARGET_FPS; // Target frame time in seconds
    private double accumulator = 0; // Time accumulator for updates
    private long lastTime = System.nanoTime(); // Last frame timestamp

    public GameLoopT(Canvas canvas, Scene scene) {
        this.gc = canvas.getGraphicsContext2D();
        this.gameWorld = new GameWorld(canvas, scene);
    }

    private void update(double deltaTime) {
        try {
            gameWorld.update(deltaTime);
        } catch (Exception e) {
            e.printStackTrace();
            // Log the exception or handle it appropriately
        }
    }

    private void render(GraphicsContext gc, double interpolation) {
        try {
            gc.clearRect(0, 0, gameWorld.CANVAS_WIDTH, gameWorld.CANVAS_HEIGHT);
            gameWorld.render(gc);
        } catch (Exception e) {
            e.printStackTrace();
            // Log the exception or handle it appropriately
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1e9;
            lastTime = now;

            accumulator += deltaTime;

            while (accumulator >= TIME_PER_FRAME) {
                update(TIME_PER_FRAME);
                accumulator -= TIME_PER_FRAME;
            }

            double interpolation = accumulator / TIME_PER_FRAME;
            render(gc, interpolation);

            long sleepTime = (long) (TIME_PER_FRAME * 1000) - (System.nanoTime() - now) / 1_000_000;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}