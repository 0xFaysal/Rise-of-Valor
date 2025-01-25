//package game.rise_of_valor.network;
//
//import game.rise_of_valor.game_engine.GameWorld;
//import game.rise_of_valor.models.PlayerUpdate;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.util.Map;
//
//public class NetworkListener implements Runnable {
//    private final ObjectInputStream in;
//    private final GameWorld gameWorld;
//
//    public NetworkListener(ObjectInputStream in, GameWorld gameWorld) {
//        this.in = in;
//        this.gameWorld = gameWorld;
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                Object input = in.readObject();
//                if (input instanceof PlayerUpdate) {
//                    gameWorld.updatePlayer((PlayerUpdate) input);
//                } else if (input instanceof Map<Integer, PlayerUpdate>) {
//                    // Update all players
//                    gameWorld.updatePlayers((Map<Integer, PlayerUpdate>) input);
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Error in network listener: " + e.getMessage());
//        }
//    }
//}
