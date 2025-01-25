//package game.rise_of_valor.network;
//
//import game.rise_of_valor.models.PlayerUpdate;
//
//import java.io.*;
//import java.net.Socket;
//
//public class GameClient {
//    private final String serverAddress;
//    private final int serverPort;
//    private Socket socket;
//    private ObjectOutputStream out;
//    private ObjectInputStream in;
//
//    public GameClient(String serverAddress, int serverPort) {
//        this.serverAddress = serverAddress;
//        this.serverPort = serverPort;
//    }
//
//    public void connect() throws IOException {
//        socket = new Socket(serverAddress, serverPort);
//        out = new ObjectOutputStream(socket.getOutputStream());
//        in = new ObjectInputStream(socket.getInputStream());
//        System.out.println("Connected to the server at " + serverAddress + ":" + serverPort);
//    }
//
//    public void sendPlayerUpdate(PlayerUpdate playerUpdate) {
//        try {
//            out.writeObject(playerUpdate);
//            out.flush();
//        } catch (IOException e) {
//            System.err.println("Error sending player update: " + e.getMessage());
//        }
//    }
//
//    public ObjectInputStream getInputStream() {
//        return in;
//    }
//
//    public void close() {
//        try {
//            if (socket != null) socket.close();
//        } catch (IOException e) {
//            System.err.println("Error closing client socket: " + e.getMessage());
//        }
//    }
//}
