package game.rise_of_valor.shareData;

public class GameCache {
    private static int killCount;
    private static int coinCount;
    private static double totalDamage;


    public static int getKillCount() {
        return killCount;
    }

    public static void setKillCount(int killCount) {
        GameCache.killCount = killCount;
    }

    public static int getCoinCount() {
        return coinCount;
    }

    public static void setCoinCount(int coinCount) {
        GameCache.coinCount = coinCount;
    }

    public static double getTotalDamage() {
        return totalDamage;
    }

    public static void setTotalDamage(double totalDamage) {
        GameCache.totalDamage = totalDamage;
    }
}
