package game.rise_of_valor.models;

import javafx.scene.image.Image;

import java.io.PipedReader;
import java.io.Serializable;

public class ClientData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String image;
    private String IP;
    private int PORT;
    private int level;



    public ClientData(String username, int level,String image) {
        this.username = username;
        this.level = level;
        this.image = image;

    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
                ", level=" + level +
                '}';
    }
}