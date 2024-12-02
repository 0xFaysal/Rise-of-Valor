package game.rise_of_valor.models;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.List;

public class Player extends Character {


    public Player(int inertiaPositionX, int inertiaPositionY) {
        super(inertiaPositionX, inertiaPositionY);
        speed = 200;
    }




    public void update(Scene scene , double deltaTime , List<KeyCode> keys) {


        if(!keys.isEmpty()){
            KeyCode key = keys.get(0);
            if(key == KeyCode.W || key == KeyCode.UP){
                inertiaPositionY -= (int) (speed * deltaTime);
            }
            if(key == KeyCode.S|| key == KeyCode.DOWN){
                inertiaPositionY += (int) (speed * deltaTime);
            }
            if(key == KeyCode.A|| key == KeyCode.LEFT){
                inertiaPositionX -= (int) (speed * deltaTime);
            }
            if(key == KeyCode.D|| key == KeyCode.RIGHT){
                inertiaPositionX += (int) (speed * deltaTime);
            }
        }
        else {
            inertiaPositionX += 0;
            inertiaPositionY += 0;
        }
    }
}