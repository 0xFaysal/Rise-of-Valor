package game.rise_of_valor.network.client;

import game.rise_of_valor.models.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

class ClientReceiverThread extends Thread {
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final Client.ConnectionListener connectionListener;

    public ClientReceiverThread(Socket socket, Client.ConnectionListener connectionListener) throws IOException {
        this.socket = socket;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.connectionListener = connectionListener;
        this.setName("ClientReceiverThread");
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                System.out.println("Waiting for messages..");
                Message receivedMessage = (Message) objectInputStream.readObject();
                if (receivedMessage.isConnectionSuccessful()) {
                    System.out.println("Connection successful");
                    if (connectionListener != null) {
                        connectionListener.onConnectionSuccessful();
                    }
                    continue;
                }
                System.out.println("\nReceived from " + receivedMessage.getUsername() + ": " + receivedMessage.getMessage());
            }
        } catch (EOFException e) {
            System.out.println("Connection closed by server.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}