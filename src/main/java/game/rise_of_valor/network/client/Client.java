package game.rise_of_valor.network.client;

import game.rise_of_valor.models.ClientData;

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
            clientSenderThread =  new ClientSenderThread(socket);
            clientSenderThread.start();
          clientReceiverThread =  new ClientReceiverThread(socket, connectionListener);
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

    public void createDueModeRoom(ClientData clientData) {
        clientSenderThread.createDueModeRoom(clientData);
    }

    public interface ConnectionListener {
        void onConnectionSuccessful();
        void onConnectionFailed();
    }
}