package game.rise_of_valor.models;

import game.rise_of_valor.shareData.UserData;
import javafx.scene.image.Image;

import java.io.PipedReader;
import java.io.Serializable;

public class ClientData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String username;

    private String password;
    private String imageString;
    private int imageId;
    private String IP;
    private int PORT;
    private int level;
    private UserData userData;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public ClientData(String username, String name, String password, int level, String image , int imageId , UserData userData) {
        this.username = username;
        this.level = level;
        this.imageString = image;
        this.imageId = imageId;
        this.name = name;
        this.password = password;
        this.userData = userData;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return imageString;
    }

    public void setImage(String image) {
        this.imageString = image;
    }

    public String getIP() {
        return IP;
    }

    public int getPORT() {
        return PORT;
    }

    public int getLevel() {
        return level;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return "ClientData{" +
                "username='" + username + '\'' +
                ", IP='" + IP + '\'' +
                ", PORT=" + PORT +
                ", image='" + imageString + '\'' +
                ", level=" + level +
                '}';
    }

    public void setPort(int port) {
        this.PORT = port;
    }
}