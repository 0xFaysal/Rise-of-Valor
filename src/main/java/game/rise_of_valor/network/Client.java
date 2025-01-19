//package game.rise_of_valor.network;
//
//import game.rise_of_valor.game_engine.GameWorldMultiplayer;
//import game.rise_of_valor.models.Bullet;
//import game.rise_of_valor.models.Enemy;
//import game.rise_of_valor.models.PlayerUpdate;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.List;
//
//public class Client {
//    private final String serverAddress;
//    private final int serverPort;
//    private final int playerId;
//    private final GameWorldMultiplayer gameWorld;
//    private ObjectInputStream in;
//    private ObjectOutputStream out;
//    private Thread listeningThread;
//
//    public Client(String serverAddress, int serverPort, int playerId, GameWorldMultiplayer gameWorld) {
//        this.serverAddress = serverAddress;
//        this.serverPort = serverPort;
//        this.playerId = playerId;
//        this.gameWorld = gameWorld;
//    }
//
//    /**
//     * Connect to the server and initialize input/output streams.
//     */
//    public void start() {
//        try {
//            Socket socket = new Socket(serverAddress, serverPort);
//            out = new ObjectOutputStream(socket.getOutputStream());
//            in = new ObjectInputStream(socket.getInputStream());
//
//            // Start listening to server updates
//            listeningThread = new Thread(this::listenToServer);
//            listeningThread.start();
//
//            System.out.println("Connected to server!");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Unable to connect to server.");
//        }
//    }
//
//    /**
//     * Send player updates to the server.
//     *
//     * @param update PlayerUpdate containing position and state data.
//     */
//    public void sendPlayerUpdate(PlayerUpdate update) {
//        try {
//            out.writeObject(update);
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Send a bullet update to the server.
//     *
//     * @param bullet Bullet to be synchronized with the server.
//     */
//    public void sendBullet(Bullet bullet) {
//        try {
//            out.writeObject(bullet);
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Listen to server updates in a separate thread.
//     */
//    private void listenToServer() {
//        try {
//            while (true) {
//                Object received = in.readObject();
//
//                // Handle received data based on its type
//                if (received instanceof PlayerUpdate) {
//                    gameWorld.handlePlayerUpdate((PlayerUpdate) received);
//                } else if (received instanceof Bullet) {
//                    gameWorld.handleBulletUpdate((Bullet) received);
//                } else if (received instanceof Enemy) {
//                    gameWorld.handleEnemyUpdate((Enemy) received);
//                } else if (received instanceof List<?>) {
//                    List<?> updates = (List<?>) received;
//
//                    if (!updates.isEmpty() && updates.get(0) instanceof Enemy) {
//                        @SuppressWarnings("unchecked")
//                        List<Enemy> enemies = (List<Enemy>) updates;
//                        for (Enemy enemy : enemies) {
//                            gameWorld.handleEnemyUpdate(enemy);
//                        }
//                    }
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            System.err.println("Connection to server lost.");
//        }
//    }
//
//    /**
//     * Close client resources.
//     */
//    public void stop() {
//        try {
//            if (listeningThread != null) {
//                listeningThread.interrupt();
//            }
//            if (in != null) {
//                in.close();
//            }
//            if (out != null) {
//                out.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public int getPlayerId() {
//        return playerId;
//    }
//
//    public List<Bullet> getPlayerSprite() {
//        // Return a sprite reference for the player's visual representation.
//        // Placeholder for actual implementation.
//        return null;
//    }
//}
