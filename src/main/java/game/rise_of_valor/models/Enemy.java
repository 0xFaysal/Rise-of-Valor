package game.rise_of_valor.models;

public class Enemy {
    private String name;
    private int health;
    private int damage;
    private int speed;
    private int reward;

    public Enemy(String name, int health, int damage, int speed, int reward) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getReward() {
        return reward;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}
