package game.rise_of_valor.network.server;

import game.rise_of_valor.models.ClientData;
import game.rise_of_valor.models.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class GameServerThread extends Thread {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final ClientHandler clientHandler;

    static boolean isRunning = true;
    private static final List<List<ClientData>> rooms = new ArrayList<>();

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
                System.out.println("Received from user-" + message.getUsername() + " : " + message);

                if ("createdue".equalsIgnoreCase(message.getMessage())) {
                    System.out.println("User " + message.getUsername() + " created a due mode room------");
                    System.out.println("Client data: " + clientHandler.getUsername());
                    ClientData clientData = message.getClientData();
                    clientData.setIP(socket.getInetAddress().getHostAddress());
                    clientData.setPort(socket.getPort());
                    joinOrCreateRoom(clientData);

                } else if ("all".equalsIgnoreCase(message.getReceiver())) {
                    GameServer.broadcastMessage(message, message.getUsername());
                } else if ("list".equalsIgnoreCase(message.getMessage())) {
                    // Send the list of active users to the requesting client
                    List<String> activeUsers = GameServer.getActiveUsers();
                    Message userListMessage = new Message("server", String.join(", ", activeUsers), message.getUsername());
                    GameServer.sendMessageToUser(userListMessage, message.getUsername());
                } else {
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

    private void joinOrCreateRoom(ClientData clientData) {
        for (List<ClientData> room : rooms) {
            if (room.size() < 2) {
                room.add(clientData);
                System.out.println("User " + clientData.getUsername() + " joined room " + rooms.indexOf(room));

                // Notify the existing player in the room
                ClientData existingPlayer = room.get(0);
                Message newPlayerMessage = new Message("newPlayer", clientData);
                System.out.println("Sending data to existing player: " + clientHandler.getUsername());
                GameServer.sendMessageToUser(newPlayerMessage, existingPlayer.getUsername());

                // Notify the new player about the existing player
                Message existingPlayerMessage = new Message("existingPlayer", existingPlayer);
                GameServer.sendMessageToUser(existingPlayerMessage, clientData.getUsername());

                return;
            }
        }
        List<ClientData> newRoom = new ArrayList<>();
        newRoom.add(clientData);
        rooms.add(newRoom);
        System.out.println("User " + clientData.getUsername() + " created a new room " + rooms.indexOf(newRoom));
    }

    public static void stopThread() {
        isRunning = false;
    }
}
