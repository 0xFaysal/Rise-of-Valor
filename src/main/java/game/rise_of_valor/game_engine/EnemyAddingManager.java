package game.rise_of_valor.game_engine;

import game.rise_of_valor.Main;
import game.rise_of_valor.models.Enemy;
import game.rise_of_valor.models.Player;
import game.rise_of_valor.models.Sprite;
import game.rise_of_valor.utils.LoadSprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static game.rise_of_valor.game_engine.MapManager.space;

public class EnemyAddingManager {

    ArrayList<Enemy> enemies;
    private final Player player;
    private final MapManager mapManager;
    private LoadSprite loadSprite;
    private double elapsedTime;
    private double spawnInterval;
    private final Random random;

    private final EnemyPool enemyPool = new EnemyPool();



    public EnemyAddingManager(ArrayList<Enemy> enemies, Player player, MapManager mapManager, LoadSprite loadSprite) {
        this.enemies = enemies;
        this.player = player;
        this.mapManager = mapManager;
        this.loadSprite = loadSprite;
        this.elapsedTime = 0;
        this.spawnInterval = 2; // Initial spawn interval in seconds
        this.random = new Random();
        System.out.println("Enemy adding manager created");
    }

    public void update(double deltaTime ,double killingRate) {
        elapsedTime += deltaTime;
//        System.out.println("Updating enemy adding manager");

        // Ensure killingRate is not NaN
        if (Double.isNaN(killingRate)) {
            killingRate = 0;
        }

        // Adjust spawn interval based on player level, gun power, and killing rate
        double playerLevel = player.getLevel();
        double gunPower = player.getGun().getPower();


        // If the total number of enemies is 0 or near 0, set a very short spawn interval
        if (enemies.size() <= 2) {
            spawnInterval = 0.5;
        } else {
            spawnInterval = Math.max(0.5, 5 - (playerLevel + gunPower + killingRate) / 3);
        }

        if (elapsedTime >= spawnInterval) {
            addEnemies(random.nextInt(8));
            elapsedTime = 0;
        }
    }

//    private void addEnemy() {
//    System.out.println("Adding enemy");
//
//       for(int i=0;i<random.nextInt(8);i++) {// Define the range within which enemies should be added around the player
//            int range = 200; // Adjust this value as needed
//
//            // Generate random positions within the specified range around the player
//            int x = (int) (player.worldPositionX + random.nextInt(range * 2) - range);
//            int y = (int) (player.worldPositionY + random.nextInt(range * 2) - range);
//
//            // Ensure the generated positions are within the map boundaries
//            x = Math.max((int) mapManager.getSpace(), Math.min(x, (int) (mapManager.getMapWidth() - space)));
//            y = Math.max((int) mapManager.getSpace(), Math.min(y, (int) (mapManager.getMapHeight() - space)));
//
//            // Choose enemy type based on player level
//            int enemyType = random.nextInt((int) player.getLevel() + 4) % 4;
//
//            Enemy enemy = new Enemy(this.loadSprite.getEnemySprite(enemyType), x, y);
//            enemies.add(enemy);
//        }
//}

    private void addEnemies(int count) {
        for (int i = 0; i < count; i++) {
            int range = 200; // Adjust this value as needed
            int x = (int) (player.worldPositionX + random.nextInt(range * 2) - range);
            int y = (int) (player.worldPositionY + random.nextInt(range * 2) - range);
            x = Math.max((int) mapManager.getSpace(), Math.min(x, (int) (mapManager.getMapWidth() - space)));
            y = Math.max((int) mapManager.getSpace(), Math.min(y, (int) (mapManager.getMapHeight() - space)));
            int enemyType = random.nextInt((int) player.getLevel() + 4) % 4;
            Enemy enemy = enemyPool.getEnemy(this.loadSprite.getEnemySprite(enemyType), x, y);
            enemies.add(enemy);
        }
    }


    public class EnemyPool {
        private final Queue<Enemy> pool = new LinkedList<>();

        public Enemy getEnemy(Sprite sprite, int x, int y) {
            if (pool.isEmpty()) {
                return new Enemy(sprite, x, y);
            } else {
                Enemy enemy = pool.poll();
                enemy.reset(sprite, x, y);
                return enemy;
            }
        }

        public void releaseEnemy(Enemy enemy) {
            pool.offer(enemy);
        }
    }
}