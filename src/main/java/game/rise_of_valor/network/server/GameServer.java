package game.rise_of_valor.network.server;

import game.rise_of_valor.models.Message;
import game.rise_of_valor.shareData.UserData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {

    private int port;
    static int userCount = 0;
    private static final List<ClientHandler> clientHandlers = new ArrayList<>();


    public GameServer() {
        this.port = 5656;
        this.setName("GameServer");
    }


    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(port);

            System.out.println("Server is running on port 1234");
            System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());

            while (!serverSocket.isClosed()) {
                userCount++;
                Socket clientSocket = serverSocket.accept();

                System.out.println("User-" + userCount + " connected");

                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ClientHandler clientHandler = new ClientHandler(clientSocket, outputStream);

                // Start the thread to handle client communication
                GameServerThread serverThread = new GameServerThread(clientSocket,outputStream);
                serverThread.start();

                // Add the client handler to the list after the username is set
                clientHandlers.add(clientHandler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void broadcastMessage(Message message, String senderUsername) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.getUsername().equals(senderUsername)) {
                try {
                    clientHandler.getOutputStream().writeObject(message);
                    clientHandler.getOutputStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendMessageToUser(Message message, String receiverUsername) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getUsername().equals(receiverUsername)) {
                try {
                    clientHandler.getOutputStream().writeObject(message);
                    clientHandler.getOutputStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

//    public static void sendPlayerData(Message message, UserData userData) {
//        for (ClientHandler clientHandler : clientHandlers) {
//            try {
//                clientHandler.getOutputStream().writeObject(message);
//                clientHandler.getOutputStream().flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void removeClientHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        System.out.println("Removed client: " + clientHandler.getUsername());
    }

    public static List<String> getActiveUsers() {
        List<String> activeUsers = new ArrayList<>();
        for (ClientHandler clientHandler : clientHandlers) {
            activeUsers.add(clientHandler.getUsername());
        }
        return activeUsers;
    }


    public void shutdown() {
        System.out.println("Shutting down server.. call from " + Thread.currentThread().getName());
        try {
            this.interrupt();
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.getOutputStream().close();
                clientHandler.getSocket().close();
                GameServerThread.isRunning = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        return port;
    }
}