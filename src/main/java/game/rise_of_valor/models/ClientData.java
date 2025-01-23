package game.rise_of_valor.models;

import java.io.Serializable;

public class ClientData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
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
    }

    public String getUsername() {
        return username;
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

    public double getLoginTime() {
        return loginTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isPlaying() {
        return isPlaying;
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

    public void setLoginTime(double loginTime) {
        this.loginTime = loginTime;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "username='" + username + '\'' +
                ", IP='" + IP + '\'' +
                ", PORT=" + PORT +
                ", level=" + level +
                ", loginTime=" + loginTime +
                ", isActive=" + isActive +
                ", isPlaying=" + isPlaying +
                '}';
    }
}