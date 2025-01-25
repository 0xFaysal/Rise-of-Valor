package game.rise_of_valor.game_engine;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.util.PrimitiveIterator;

public class GameLoop {

    private GraphicsContext gc;
    private GameWorld gameWorld;
//    private GameWorldV2 gameWorld;
    // Constants for fixed time step game loop
    private static final int TARGET_FPS = 60; // Target frames per second
    private static final double TIME_PER_FRAME = 1.0 / TARGET_FPS; // Target frame time in seconds
    private double accumulator = 0; // Time accumulator for updates
    private long lastTime = 0; // Last frame timestamp

    public GameLoop(Canvas canvas, Scene scene) {
        this.gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        gameWorld = new GameWorld(canvas, scene);
//        gameWorld = new GameWorldV2(canvas, scene);
        System.out.println("Game loop created");

        AnimationTimer gameLoop1 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Calculate delta time in seconds
                double deltaTime = (now - lastTime) / 1e9;

                // Accumulate time
                accumulator += deltaTime;

                // Update at fixed intervals
                while (accumulator >= TIME_PER_FRAME) {
                    update(TIME_PER_FRAME);
                    accumulator -= TIME_PER_FRAME;
                }

                // Render with interpolation for smoothness
//                double interpolation = accumulator / TIME_PER_FRAME;
//                render(gc, interpolation);

                // Print FPS
//                System.out.println(1 / deltaTime);

                lastTime = now;

            }
        };
        AnimationTimer gameLoop2 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Calculate delta time in seconds
                double deltaTime = (now - lastTime) / 1e9;

                // Accumulate time
                accumulator += deltaTime;

                // Update at fixed intervals
                while (accumulator >= TIME_PER_FRAME) {
//                    update(TIME_PER_FRAME);
                    accumulator -= TIME_PER_FRAME;
                }

                // Render with interpolation for smoothness
                double interpolation = accumulator / TIME_PER_FRAME;
                render(gc, interpolation);

                // Print FPS
//                System.out.println(1 / deltaTime);

                lastTime = now;

                if (gameWorld.getTopViewManager().getTimer().isTimeUp()) {
                    loadWinBGView(scene);
                    stop();
                }

            }
        };

        gameLoop1.start();
        gameLoop2.start();
    }

    private void update(double deltaTime) {
        gameWorld.update(deltaTime);
    }

    private void render(GraphicsContext gc, double interpolation) {
        gc.clearRect(0, 0, gameWorld.CANVAS_WIDTH, gameWorld.CANVAS_HEIGHT);
        gameWorld.render(gc);
    }

    private void loadWinBGView(Scene scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/winBG-view.fxml"));
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public Timer getTimer(){
        return gameWorld.getTopViewManager().getTimer();
   }

}