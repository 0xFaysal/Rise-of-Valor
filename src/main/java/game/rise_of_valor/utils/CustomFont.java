package game.rise_of_valor.utils;

import javafx.scene.text.Font;

import static javafx.scene.text.FontWeight.BOLD;

public class CustomFont {

//    public static final String AttackOfMonster = "/game/rise_of_valor/assets/fonts/Attack Of Monster.ttf";
//    public static final String Inter = "/game/rise_of_valor/assets/fonts/Inter_24pt-Regular.ttf";
    public Font AttackOfMonster;
    public Font Inter;
    public Font Desporm;


    public CustomFont() {
        AttackOfMonster = Font.loadFont(getClass().getResourceAsStream("/game/rise_of_valor/assets/fonts/Attack Of Monster.ttf"), 24);
        Inter = Font.loadFont(getClass().getResourceAsStream("/game/rise_of_valor/assets/fonts/Inter_24pt-Regular.ttf"), 24);
        Desporm = Font.loadFont(getClass().getResourceAsStream("/game/rise_of_valor/assets/fonts/DESPORM.ttf"), 24);


        System.out.println("Loading fonts");
    }

    public Font getAttackOfMonster() {
        return AttackOfMonster;
    }
    public Font getInter() {
        return Inter;
    }
    public Font getDesporm() {
        return Desporm;
    }

}
