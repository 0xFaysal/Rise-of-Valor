package game.rise_of_valor.models;

import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String mode;
    private ClientData clientData;
    private String message;
    private String receiver;
    private boolean isConnectionSuccessful;





    public Message(String mode, boolean isConnectionSuccessful) {
        this.mode = mode;
        this.isConnectionSuccessful = isConnectionSuccessful;
    }

    public Message(String mode, ClientData clientData) {
        this.mode = mode;
        this.clientData = clientData;
    }

    public Message( String messageText, String receiver) {
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

public String getMode() {
        return mode;
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