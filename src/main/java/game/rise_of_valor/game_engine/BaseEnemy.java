package game.rise_of_valor.game_engine;


import game.rise_of_valor.effects.PortalEffect;
import game.rise_of_valor.models.*;
import game.rise_of_valor.models.Character;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public abstract class BaseEnemy extends Character {
    protected double fireCooldown = 2.0;
    protected double timeSinceLastFire = 0;

    protected int currentCharacterId ;
    private PortalEffect portalEffect;
    private double appearanceHeight;
    private double appearanceOpacity;
    private boolean appearanceFinished;

    public BaseEnemy(Sprite sprite, int worldPositionX, int worldPositionY) {
        super(worldPositionX, worldPositionY);
        movement = sprite.movement;
        idle = sprite.idle;
        scaleFactor = 15;
        spriteCount = movementSpriteCount - 1;
    }

    public void update(double deltaTime, Player player, List<EnemyBullet> bullets){
        super.update(deltaTime);
        // Ensure currentSprite is within valid range
        if (currentSprite < 0 || currentSprite >= spriteCount) {
            currentSprite = 0;
        }

        spriteCount = isMoving ? movementSpriteCount - 1 : (idleSpriteCount > 0 ? idleSpriteCount - 1 : movementSpriteCount - 1);

        // Ensure currentSprite is within valid range
        if (currentSprite < 0 || currentSprite >= spriteCount) {
            currentSprite = 0;
        }

        bodyX = worldPositionX + bodyOffsetX;
        bodyY = worldPositionY + bodyOffsetY;
        if (facingLeft) {
            switch (currentCharacterId) {
                case 1, 3 -> bodyX = worldPositionX + bodyOffsetX - 3;
                case 2 -> bodyX = worldPositionX + bodyOffsetX;
                case 4 -> bodyX = worldPositionX + bodyOffsetX - 10;
            }
        }
    }

    public void draw(GraphicsContext gc){
        if (portalEffect.isVisible()) {
            portalEffect.draw(gc);
            if (portalEffect.isExpired() && appearanceFinished) {
                super.draw(gc);
            } else {
                gc.setGlobalAlpha(appearanceOpacity);
//                gc.drawImage(movement.get(currentSprite), spriteX, spriteY - appearanceHeight, spriteWidth * scaleFactor, spriteHeight * scaleFactor, worldPositionX, worldPositionY, spriteWidth, spriteHeight);
                gc.setGlobalAlpha(1);
            }

        } else if (appearanceFinished) {
            super.draw(gc);
        }
    }

    public abstract void move(double deltaTime, Player player, List<BaseEnemy> enemies);
    public int getCurrentCharacterId() {
        return currentCharacterId;
    }
}
