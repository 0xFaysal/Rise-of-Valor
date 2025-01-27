package game.rise_of_valor.network.server;

import game.rise_of_valor.models.Message;

import java.io.ObjectOutputStream;

public class ServerSenderThread extends Thread {

    ObjectOutputStream objectOutputStream;

    Message message;
    public ServerSenderThread(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
        this.setName("ServerSenderThread");
    }
    @Override
    public void run() {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void sendToClients(Message msg) {
        message = msg;
        this.start();
    }
}
