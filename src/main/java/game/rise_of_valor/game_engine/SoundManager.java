package game.rise_of_valor.game_engine;

import game.rise_of_valor.Main;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {



    public static void playBackgroundMusic(String filePath, double volume) {
        MediaPlayer backgroundMusicPlayer;
        Media media = new Media(Main.class.getResource(filePath).toString());
        backgroundMusicPlayer = new MediaPlayer(media);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
        backgroundMusicPlayer.setVolume(volume);
        backgroundMusicPlayer.play();
    }

    private void playSoundEffect(String filePath, double volume) {
        MediaPlayer soundEffectPlayer;
        Media media = new Media(getClass().getResource(filePath).toString());
        soundEffectPlayer = new MediaPlayer(media);
        soundEffectPlayer.setVolume(volume);
        soundEffectPlayer.play();
    }
}
