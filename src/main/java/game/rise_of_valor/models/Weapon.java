package game.rise_of_valor.models;

public class Weapon {
    private String name;
    private int damage;
    private int range;
    private int attackSpeed;
    private int cost;

    public Weapon(String name, int damage, int range, int attackSpeed, int cost) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
