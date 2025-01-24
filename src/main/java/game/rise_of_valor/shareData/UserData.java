package game.rise_of_valor.shareData;

import javafx.scene.image.Image;

public class UserData {
    private static String name;
    private static String userName;
    private static String password;
    private static int level;
    private static int coin;
    private static Image profilePic;

    private static String profilePicName;
    private static boolean isAgree;
    private static boolean isPlaying;

    public UserData() {
    }

    public static void setData(String userName, String name, String password, int level, int coin, Image profilePic, boolean isPlaying, boolean isAgree) {
        UserData.name = name;
        UserData.userName = userName;
        UserData.password = password;
        UserData.level = level;
        UserData.coin = coin;
        UserData.profilePic = profilePic;
        UserData.isPlaying = isPlaying;
        UserData.isAgree = isAgree;
    }

    public static boolean isIsAgree() {
        return isAgree;
    }

    public static void setIsAgree(boolean isAgree) {
        UserData.isAgree = isAgree;
    }
    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserData.userName = userName;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserData.name = name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserData.password = password;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        UserData.level = level;
    }

    public static int getCoin() {
        return coin;
    }

    public static void setCoin(int coin) {
        UserData.coin = coin;
    }

    public static Image getProfilePic() {
        return profilePic;
    }

    public static void setProfilePic(Image profilePic) {
        UserData.profilePic = profilePic;
    }

    public static boolean isIsPlaying() {
        return isPlaying;
    }

    public static void setIsPlaying(boolean isPlaying) {
        UserData.isPlaying = isPlaying;
    }

    public static String getProfilePicName() {
        return profilePicName;
    }

    public static void setProfilePicName(String profilePicName) {
        UserData.profilePicName = profilePicName;
    }

    @Override
    public String toString() {
        return "UserData{" +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                ", coin=" + coin +
                ", profilePic=" + profilePic +
                ", isPlaying=" + isPlaying +
                ", isAgree=" + isAgree +
                '}';
    }
}
