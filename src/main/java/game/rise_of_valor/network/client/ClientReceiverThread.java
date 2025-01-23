package game.rise_of_valor.network.client;

<<<<<<< HEAD
import game.rise_of_valor.models.ClientData;
import game.rise_of_valor.models.Message;
import game.rise_of_valor.shareData.DataManager;
import javafx.application.Platform;
=======
import game.rise_of_valor.models.Message;
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)

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
<<<<<<< HEAD

                Message message = (Message) objectInputStream.readObject();

                System.out.println("Received message: " + message);

                if (message.isConnectionSuccessful()) {
=======
                Message receivedMessage = (Message) objectInputStream.readObject();
                if (receivedMessage.isConnectionSuccessful()) {
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
                    System.out.println("Connection successful");
                    if (connectionListener != null) {
                        connectionListener.onConnectionSuccessful();
                    }
                    continue;
                }
<<<<<<< HEAD

                if ("newPlayer".equalsIgnoreCase(message.getMessage()) || "existingPlayer".equalsIgnoreCase(message.getMessage())) {
                    ClientData playerData = message.getClientData();
                    System.out.println("Received player data: " + playerData);
                }
                System.out.println("\nReceived from " + message.getUsername() + ": " + message.getMessage());
=======
                System.out.println("\nReceived from " + receivedMessage.getUsername() + ": " + receivedMessage.getMessage());
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
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