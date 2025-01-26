package game.rise_of_valor.controllers;

import game.rise_of_valor.utils.LoadSprite;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static game.rise_of_valor.controllers.LoadingController.dataManager;

public class LobbyViewController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private Canvas canvas;



    private GraphicsContext gc;



    private PlayerCharacterAnimation playerCharacterAnimation;

    private Image image = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/lobby-bg.jpeg"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        playerCharacterAnimation = new PlayerCharacterAnimation();


        gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
        playerCharacterAnimation.lobbyLoop.start();


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/rise_of_valor/fxml/lobby-top-view.fxml"));
            AnchorPane topView = loader.load();
            rootPane.getChildren().add(topView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a listener to set the user data when the scene is available
        rootPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setUserData(this);
            }
        });

    }


    public void stopLobbyLoop() {
        if (playerCharacterAnimation != null && playerCharacterAnimation.lobbyLoop != null) {
            playerCharacterAnimation.lobbyLoop.stop();
        }
    }

    public void print() {
        System.out.println("LobbyViewController print method called");
    }



     class PlayerCharacterAnimation{

         // Constants for fixed time step game loop
         private static final int TARGET_FPS = 60; // Target frames per second
         private static final double TIME_PER_FRAME = 1.0 / TARGET_FPS; // Target frame time in seconds
         private double accumulator = 0; // Time accumulator for updates
         private long lastTime = 0; // Last frame timestamp

         AnimationTimer lobbyLoop;

         int currentSprite = 0;
         double animationTime = 0;
         double baseFrameDuration = 1; // Base duration of each frame in seconds
         int spriteAnimationFactor = 190;

         private List<Image> playerSprite;
         int spriteCount = 0;

         RadialGradient shadowGradient;
        public PlayerCharacterAnimation(){

            playerSprite = dataManager.getLoadSprite().getPlayerSprite().idle;
            spriteCount = playerSprite.size()-1;

             lobbyLoop = new AnimationTimer() {
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
                    render(gc);

//                    // Print FPS
//                    System.out.println(1 / deltaTime);

                    lastTime = now;
                }
            };


            shadowGradient = new RadialGradient(
                    0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.rgb(0, 0, 0, 0.7)),
                    new Stop(0.2, Color.rgb(0, 0, 0, 0.5)),
                    new Stop(0.5, Color.rgb(0, 0, 0, 0.4)),
                    new Stop(0.7, Color.rgb(0, 0, 0, 0.3)),
                    new Stop(0.9, Color.rgb(0, 0, 0, 0.1)),
                    new Stop(1, Color.rgb(0, 0, 0, 0))
            );

        }

         private void update(double deltaTime) {
             // Calculate frame duration based on speed
             double frameDuration = baseFrameDuration / (spriteAnimationFactor / 32.0);

             // Update animation time
             animationTime += deltaTime;
             if (animationTime >= frameDuration) {
                 currentSprite++;
                 animationTime -= frameDuration;
//            System.out.println("Current sprite: " + currentSprite);
             }
             if (currentSprite > spriteCount) {
                 currentSprite = 0;
             }

         }

        private void render(GraphicsContext gc) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
            Image player = playerSprite.get(currentSprite);




            // Calculate shadow width using a sine wave function for smooth animation
            double time = System.currentTimeMillis() / 1000.0;
            double shadowWidth = 90 + 5 * Math.sin(time * 1.8 * Math.PI); // Adjust amplitude and frequency as needed
            // Calculate shadow position based on player's position
            double shadowXOffset = (1000 - shadowWidth) / 2;


            // Draw realistic shadow under the character
            gc.setFill(shadowGradient);
            gc.fillOval(shadowXOffset, 345, shadowWidth, 20);


//            System.out.println(currentSprite+player.getUrl());
            gc.drawImage(player, 435, 208,player.getWidth()*0.2*2,player.getHeight()*0.2*2);
        }
    }
}