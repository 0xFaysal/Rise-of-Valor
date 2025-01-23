package game.rise_of_valor.network.server;

import game.rise_of_valor.models.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

class GameServerThread extends Thread {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final ClientHandler clientHandler;

    static boolean isRunning = true;

    public GameServerThread(Socket socket, ObjectOutputStream objectOutputStream, ClientHandler clientHandler) throws IOException {
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.clientHandler = clientHandler;
        this.setName("GameServerThread");
    }

    @Override
    public void run() {
        try {
            // Register the client with the provided username
            Message initialMessage = (Message) objectInputStream.readObject();
            System.out.println("Received initial message: " + initialMessage);
            clientHandler.setUsername(initialMessage.getUsername());
            objectOutputStream.writeObject(new Message(true));

            while (socket.isConnected() && isRunning) {
                Message message = (Message) objectInputStream.readObject();
                System.out.println();
                System.out.println(STR."Received from user-\{message.getUsername()} :\{message}");
                if ("all".equalsIgnoreCase(message.getReceiver())) {
                    GameServer.broadcastMessage(message, message.getUsername());
                }
                else if ("list".equalsIgnoreCase(message.getMessage())) {
                    // Send the list of active users to the requesting client
                    List<String> activeUsers = GameServer.getActiveUsers();
                    Message userListMessage = new Message("server", String.join(", ", activeUsers), message.getUsername());
                    GameServer.sendMessageToUser(userListMessage, message.getUsername());
                }
                else {
                    GameServer.sendMessageToUser(message, message.getReceiver());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if (e instanceof IOException && e.getMessage().contains("Connection reset")) {
                System.out.println("User " + clientHandler.getUsername() + " disconnected.");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                GameServer.removeClientHandler(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopThread() {
        isRunning = false;
    }
}
