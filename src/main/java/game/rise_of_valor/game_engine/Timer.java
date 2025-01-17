package game.rise_of_valor.game_engine;

import game.rise_of_valor.utils.CustomFont;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static game.rise_of_valor.game_engine.DataManager.getBackgroundTimer;

public class Timer {
    private double time; // time in seconds

    private Font customFont;
    Image background;


    public Timer(double duration, Font font) {
        this.time = duration;
        customFont = Font.font(font.getFamily(), FontWeight.BOLD,30);
        background = getBackgroundTimer();
    }

    public void update(double deltaTime) {
        if (time > 0) {
            time -= deltaTime;
        }
    }

    public void draw(GraphicsContext gc, double canvasWidth) {
        gc.drawImage(background, canvasWidth / 2 - 92, 8, 200, 50);
        int minutes = (int) (time / 60);
        int seconds = (int) (time % 60);
        String timeString = String.format("%02d:%02d", minutes, seconds);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 14.0));
        gc.fillText("Time", canvasWidth / 2 - 9, 25); // Adjust the position as needed
        gc.setFont(customFont);
        gc.fillText(timeString, canvasWidth / 2 - 30, 50); // Adjust the position as needed
    }

    public boolean isTimeUp() {
        return time <= 0;
    }

    public void restartTimer(double duration) {
        time = duration;
    }
}