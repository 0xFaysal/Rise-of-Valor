package game.rise_of_valor.game_engine;

import game.rise_of_valor.utils.CustomFont;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static game.rise_of_valor.shareData.DataManager.coinIcon;

public class TopViewManager {

    private CustomFont customFont;
    private Timer timer;

    private int remainEnemy;
    private int killedEnemy;
    private Image gearIcon;
    private Image healthBar;

    private int coinCount;

    double playerLife ;

    public TopViewManager(CustomFont customFont) {
        this.customFont = customFont;
        this.timer = new Timer(60, customFont.getDesporm());
        this.remainEnemy = 0;
        this.killedEnemy = 0;
        this.gearIcon = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/pause.png"));
        this.healthBar = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/images/healthbar.png"));
    }

    public void setRemainEnemy(int remainEnemy) {
        this.remainEnemy = remainEnemy;
    }

    public void updateKilledEnemy() {
        this.killedEnemy += 1;
    }


    public void update(double deltaTime) {
        timer.update(deltaTime);
    }

    public void draw(GraphicsContext gc, double canvasWidth,double canvasHeight) {
        timer.draw(gc, canvasWidth);

        gc.setFill(Color.color(0.0, 0.0, 0.0, 0.6));
        gc.fillRect(15, 15, 85, 30);
        gc.fillRect(110, 15, 65, 30);
        gc.fillOval(canvasWidth-50, 15, 30, 30);



        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeRect(15, 15, 85, 30);
        gc.strokeRect(110, 15, 65, 30);
        gc.strokeOval(canvasWidth-50, 15, 30, 30);


        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(customFont.getDesporm().getFamily(), FontWeight.EXTRA_BOLD,14));
        String rematinEnemyString = String.format("Remain : %02d", remainEnemy);
        gc.fillText(rematinEnemyString, 20, 35);
        String killedEnemyString = String.format("Kill : %02d", killedEnemy);
        gc.fillText(killedEnemyString, 115, 35);
        gc.drawImage(gearIcon, canvasWidth-45, 20, 20, 20);
        gc.setFont(Font.font(customFont.getDesporm().getFamily(), FontWeight.EXTRA_BOLD,10));
        gc.setFill(Color.YELLOW);
        gc.fillText("Esc", canvasWidth-25, 20);

       //health bar
        gc.setFill(Color.RED);
        gc.fillRect(25, 65, (healthBar.getWidth()*0.6-21)*(playerLife/100),12);
        gc.drawImage(healthBar, 15,60, healthBar.getWidth()*0.6, healthBar.getHeight()*0.6);

        //coin
//        gc.setFill(Color.GOLD);
//        gc.fillOval(120, 60, 15, 15);
        gc.drawImage(coinIcon, canvasWidth -250 , 15, 30, 30);
        gc.setFont(Font.font(customFont.getDesporm().getFamily(), FontWeight.EXTRA_BOLD,16));
        gc.setFill(Color.WHITE);
        gc.fillText(String.valueOf(coinCount), canvasWidth-210, 35);



    }

    public void setPlayerLife(double playerLife) {
        this.playerLife = playerLife;
    }


    public void reset() {
        this.remainEnemy = 0;
        this.killedEnemy = 0;
        this.timer.restartTimer(60);
    }

    public double getKillingRate() {
        return (double) killedEnemy / (killedEnemy + remainEnemy);
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public Timer getTimer() {
        return timer;
    }
}