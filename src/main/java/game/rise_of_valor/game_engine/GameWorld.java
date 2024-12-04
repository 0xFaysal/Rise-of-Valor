package game.rise_of_valor.game_engine;

import game.rise_of_valor.models.Player;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    public final int GAME_WIDTH, GAME_HEIGHT;

    Canvas canvas;
    Scene scene;
    private final List<KeyCode> keys = new ArrayList<>();


    Player player ;
//    Player player2;

   public GameWorld(Canvas canvas, Scene scene) {
       this.canvas = canvas;
       this.scene = scene;
       this.GAME_WIDTH = (int) canvas.getWidth();
       this.GAME_HEIGHT = (int) canvas.getHeight();
         player = new Player(100, 100);
//            player2 = new Player(200, 200);



       // Add key press listener
       scene.setOnKeyPressed(e -> {
           KeyCode key = e.getCode();
           if (!keys.contains(key)) {
               keys.add(0, key); // Add key to the beginning of the list
           }
       });

       // Add key release listener
       scene.setOnKeyReleased(e -> {
           KeyCode key = e.getCode();
           keys.remove(key); // Remove key when released
       });
   }

    public void update(double deltaTime) {
        player.update(scene , deltaTime, keys);
//        player2.update(scene , deltaTime, keys);
    }
    public void render(GraphicsContext gc) {
        player.draw(gc);
//        player2.draw(gc);
    }
}
