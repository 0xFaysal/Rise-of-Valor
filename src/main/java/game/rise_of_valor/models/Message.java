package game.rise_of_valor.models;

import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
<<<<<<< HEAD
    private String mode;
    private ClientData clientData;

    private String message;
    private String receiver;
=======
    private String message;
    private String receiver;
    private Player player;
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
    private boolean isConnectionSuccessful;

//    public Message(String message) {
//        this.message = message;
//    }

    public Message(String username) {
        this.username = username;
    }
<<<<<<< HEAD


=======
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
    public Message(boolean isConnectionSuccessful) {
        this.isConnectionSuccessful = isConnectionSuccessful;
    }

<<<<<<< HEAD
    public Message(String message, ClientData clientData) {
        this.message = message;
        this.clientData = clientData;
=======
    public Message(String message, String receiver) {
        this.message = message;
        this.receiver = receiver;
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
    }

    public Message(String username, String messageText, String receiver) {
        this.username = username;
        this.message = messageText;
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
<<<<<<< HEAD
=======
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)

    public boolean isConnectionSuccessful() {
        return isConnectionSuccessful;
    }
    public void setConnectionSuccessful(boolean connectionSuccessful) {
        isConnectionSuccessful = connectionSuccessful;
    }
    @Override
    public String toString() {
<<<<<<< HEAD
        return "Message{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", receiver='" + receiver + '\'' +
                ", clientData=" + clientData +
                ", isConnectionSuccessful=" + isConnectionSuccessful +
                '}';
    }

    public ClientData getClientData() {
        return clientData;
=======
        return "Message [message=" + message + ", receiver=" + receiver + ", username=" + username + "]";
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
    }
}