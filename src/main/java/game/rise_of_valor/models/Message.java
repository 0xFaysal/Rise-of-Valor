package game.rise_of_valor.models;

import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String mode;
    private ClientData clientData;

    private String message;
    private String receiver;
    private boolean isConnectionSuccessful;

//    public Message(String message) {
//        this.message = message;
//    }

    public Message(String username) {
        this.username = username;
    }


    public Message(boolean isConnectionSuccessful) {
        this.isConnectionSuccessful = isConnectionSuccessful;
    }

    public Message(String message, ClientData clientData) {
        this.message = message;
        this.clientData = clientData;
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

    public boolean isConnectionSuccessful() {
        return isConnectionSuccessful;
    }
    public void setConnectionSuccessful(boolean connectionSuccessful) {
        isConnectionSuccessful = connectionSuccessful;
    }
    @Override
    public String toString() {
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
    }
}