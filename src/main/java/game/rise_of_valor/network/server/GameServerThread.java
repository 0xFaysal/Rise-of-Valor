package game.rise_of_valor.network.server;

<<<<<<< HEAD
import game.rise_of_valor.models.ClientData;
=======
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
import game.rise_of_valor.models.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
import java.util.List;

class GameServerThread extends Thread {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final ClientHandler clientHandler;

    static boolean isRunning = true;
<<<<<<< HEAD
    private static final List<List<ClientData>> rooms = new ArrayList<>();
=======
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)

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
<<<<<<< HEAD

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
=======
                Message message = (Message) objectInputStream.readObject();
                System.out.println();
                System.out.println(STR."Received from user-\{message.getUsername()} :\{message}");
                if ("all".equalsIgnoreCase(message.getReceiver())) {
                    GameServer.broadcastMessage(message, message.getUsername());
                }
                else if ("list".equalsIgnoreCase(message.getMessage())) {
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
                    // Send the list of active users to the requesting client
                    List<String> activeUsers = GameServer.getActiveUsers();
                    Message userListMessage = new Message("server", String.join(", ", activeUsers), message.getUsername());
                    GameServer.sendMessageToUser(userListMessage, message.getUsername());
<<<<<<< HEAD
                } else {
                    GameServer.sendMessageToUser(message, message.getReceiver());
                }

=======
                }
                else {
                    GameServer.sendMessageToUser(message, message.getReceiver());
                }
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
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

<<<<<<< HEAD
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

=======
>>>>>>> 580b71d (server created and clien , server created and connection handle done.)
    public static void stopThread() {
        isRunning = false;
    }
}
