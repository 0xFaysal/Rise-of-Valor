package game.rise_of_valor.network.client;

import game.rise_of_valor.models.ClientData;
import game.rise_of_valor.models.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ClientSenderThread extends Thread {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final BlockingQueue<Message> messageQueue;

    public ClientSenderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.messageQueue = new LinkedBlockingQueue<>();
        this.setName("ClientSenderThread");
    }


    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = messageQueue.take();
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        messageQueue.offer(message);
    }
}