package game.rise_of_valor.models;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Sprite {

    //movement actions of character CONSTANTS
    public static final String WALK = "walk";
    public static final String IDLE = "idle";
    public static final String FLY = "fly";
    public List<Image> idle = new ArrayList<>();
    public List<Image> movement = new ArrayList<>();
    public int currentCharacterId;
}
