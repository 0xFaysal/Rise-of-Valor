package game.rise_of_valor.utils;

import game.rise_of_valor.models.Sprite;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.embed.swing.SwingFXUtils;

public class LoadSprite {

    int playerCharacterId = 2;

    private String SPRITE_PATH_TEMPLATE_PLAYER = "/game/rise_of_valor/assets/sprites/player%d/%s_%d.png";
    private String SPRITE_PATH_TEMPLATE_ENEMY = "/game/rise_of_valor/assets/sprites/enemy%d/%s_%d.png";

    Sprite PlayerSprite = new Sprite();
    ArrayList<Sprite> EnemySprite = new ArrayList<>();

    public LoadSprite() {
        System.out.println("Loading sprites");
        //load enemy sprites
        Sprite enemySprite;
        for (int i = 1; i <= 3; i++) {
            enemySprite = new Sprite();
            enemySprite.currentCharacterId = i;
            loadSprites(Sprite.WALK, 7, enemySprite.movement, i, SPRITE_PATH_TEMPLATE_ENEMY);
            loadSprites(Sprite.IDLE, 5, enemySprite.idle, i, SPRITE_PATH_TEMPLATE_ENEMY);
            EnemySprite.add(enemySprite);
        }
        enemySprite = new Sprite();
        enemySprite.currentCharacterId = 4;
        loadSprites(Sprite.FLY, 5, enemySprite.movement, 4, SPRITE_PATH_TEMPLATE_ENEMY);
        EnemySprite.add(enemySprite);
        System.out.println(EnemySprite.size());

    }

    public void loadPlayer(int playerCharacterId) {
        System.out.println("Loading player sprites");
        this.playerCharacterId = playerCharacterId;
        loadSprites(Sprite.WALK, 7, PlayerSprite.movement, playerCharacterId, SPRITE_PATH_TEMPLATE_PLAYER);
        loadSprites(Sprite.IDLE, 5, PlayerSprite.idle, playerCharacterId, SPRITE_PATH_TEMPLATE_PLAYER);
    }
    public Sprite getPlayerSprite() {
        return PlayerSprite;
    }

    public Sprite getEnemySprite(int enemyId) {
        return EnemySprite.get(enemyId);
    }

    public void loadSprites(String action, int count, List<Image> spriteList, int playerCharacterId, String template) {


        for (int i = 0; i <= count; i++) {
            try (InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(
                    String.format(template, playerCharacterId, action, i)))) {
                BufferedImage bufferedImage = ImageIO.read(is);
                spriteList.add(SwingFXUtils.toFXImage(bufferedImage, null));
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                spriteList.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(
//                        String.format(template, playerCharacterId, action, i)))));
//            } catch (Exception e) {
//                System.err.println("Error loading sprite "+action+": " + e.getMessage());
//            }
        }
    }
}


