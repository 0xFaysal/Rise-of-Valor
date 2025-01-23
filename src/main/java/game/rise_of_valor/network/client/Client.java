package game.rise_of_valor.network.client;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    String hostAddress;
    int port;
    Socket socket;
    private ConnectionListener connectionListener;

   private boolean isClientConnected = false;
    public Client(String hostAddress, int port) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.setName("Client");

    }

    public Client(String hostAddress, int port, ConnectionListener connectionListener) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.connectionListener = connectionListener;
        this.setName("Client");
    }

    @Override
    public void run() {
        try {
            socket = new Socket(hostAddress, port);
            System.out.println("Connected to server on port " + port);
            new ClientSenderThread(socket).start();
            new ClientReceiverThread(socket, connectionListener).start();
        } catch (IOException e) {
            System.out.println("Failed to connect to server on port " + port);
            e.printStackTrace();
            if (connectionListener != null) {
                connectionListener.onConnectionFailed();
            }
        }
    }

    public void shutdown() {
        System.out.println("Shutting down client..");
        this.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface ConnectionListener {
        void onConnectionSuccessful();
        void onConnectionFailed();
    }
}