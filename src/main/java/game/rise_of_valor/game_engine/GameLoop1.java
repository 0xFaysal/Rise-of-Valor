package game.rise_of_valor.game_engine;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop1 {

    private final GraphicsContext gc;
    private final GameWorld gameWorld;

    private static final int TARGET_FPS = 60; // Target frames per second
    private static final double TIME_PER_FRAME = 1.0 / TARGET_FPS; // Target frame time in seconds
    private double accumulator = 0; // Time accumulator for updates
    private long lastTime = 0; // Last frame timestamp

    public GameLoop1(Canvas canvas, Scene scene) {
        this.gc = canvas.getGraphicsContext2D();
        this.gameWorld = new GameWorld(canvas, scene);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Calculate delta time in seconds
                double deltaTime = (now - lastTime) / 1e9;
                lastTime = now;

                // Cap deltaTime to avoid spiral of death
                deltaTime = Math.min(deltaTime, 0.1);

                // Accumulate time
                accumulator += deltaTime;

                // Update at fixed intervals
                while (accumulator >= TIME_PER_FRAME) {
                    update(TIME_PER_FRAME);
                    accumulator -= TIME_PER_FRAME;
                }

                // Render with interpolation
                double interpolation = accumulator / TIME_PER_FRAME;
                render(interpolation);
            }
        };

        gameLoop.start();
    }

    private void update(double deltaTime) {
        gameWorld.update(deltaTime);
    }

    private void render(double interpolation) {
        gc.clearRect(0, 0, gameWorld.CANVAS_WIDTH, gameWorld.CANVAS_HEIGHT);
        gameWorld.render(gc);
    }
}
