package game.rise_of_valor.network.client;

import game.rise_of_valor.models.ClientData;
import game.rise_of_valor.models.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

class ClientSenderThread extends Thread {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;

    private Message message;

    public ClientSenderThread(Socket socket) throws IOException {
        this.socket = socket;
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.setName("ClientSenderThread");
    }


    @Override
    public void run() {
        try{
//            Message message = new Message("connection",false);
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message){
        this.message = message;
        this.start();
    }


}