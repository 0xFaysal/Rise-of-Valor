package game.rise_of_valor.models;

<<<<<<< HEAD
import javafx.scene.image.Image;

import java.io.PipedReader;
=======
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
import java.io.Serializable;

public class ClientData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
<<<<<<< HEAD
    private String image;
    private String IP;
    private int PORT;
    private int level;



    public ClientData(String username, int level,String image) {
        this.username = username;
        this.level = level;
        this.image = image;

=======
    private String IP;
    private int PORT;
    private int level;
    private double loginTime;
    private boolean isActive;
    private boolean isPlaying;


    public ClientData(String username, String IP, int PORT, int level, double loginTime, boolean isActive, boolean isPlaying) {
        this.username = username;
        this.IP = IP;
        this.PORT = PORT;
        this.level = level;
        this.loginTime = loginTime;
        this.isActive = isActive;
        this.isPlaying = isPlaying;
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
    }

    public String getUsername() {
        return username;
    }

<<<<<<< HEAD
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

=======
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
    public String getIP() {
        return IP;
    }

    public int getPORT() {
        return PORT;
    }

    public int getLevel() {
        return level;
    }

<<<<<<< HEAD

=======
    public double getLoginTime() {
        return loginTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)

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

<<<<<<< HEAD
=======
    public void setLoginTime(double loginTime) {
        this.loginTime = loginTime;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)

    @Override
    public String toString() {
        return "ClientData{" +
                "username='" + username + '\'' +
                ", IP='" + IP + '\'' +
                ", PORT=" + PORT +
<<<<<<< HEAD
                ", image='" + image + '\'' +
                ", level=" + level +
                '}';
    }

    public void setPort(int port) {
        this.PORT = port;
    }
=======
                ", level=" + level +
                ", loginTime=" + loginTime +
                ", isActive=" + isActive +
                ", isPlaying=" + isPlaying +
                '}';
    }
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
}