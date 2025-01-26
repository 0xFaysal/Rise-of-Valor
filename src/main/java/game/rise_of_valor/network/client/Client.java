package game.rise_of_valor.network.client;

import game.rise_of_valor.models.Message;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    String hostAddress;
    int port;
    Socket socket;

    ClientSenderThread clientSenderThread;
    ClientReceiverThread clientReceiverThread;

    private ConnectionListener connectionListener;

    private boolean isClientConnected = false;

    public Client(String hostAddress, int port) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.setName("Client");
        initializeClientSenderThread();
    }

    public Client(String hostAddress, int port, ConnectionListener connectionListener) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.connectionListener = connectionListener;
        this.setName("Client");
        initializeClientSenderThread();
    }

    private void initializeClientSenderThread() {
        try {
            socket = new Socket(hostAddress, port);
            clientSenderThread = new ClientSenderThread(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        try {
            System.out.println("Connected to server on port " + port);
            clientReceiverThread = new ClientReceiverThread(socket, connectionListener);
            clientReceiverThread.start();
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

    public void sendMessage(Message message) {
        if (clientSenderThread != null) {
            clientSenderThread.sendMessage(message);
        } else {
            System.out.println("ClientSenderThread is not initialized. Cannot send message.");
        }
    }

    public interface ConnectionListener {
        void onConnectionSuccessful();
        void onConnectionFailed();
    }
}