package game.rise_of_valor.server_controllers;

//import game.rise_of_valor.server.GameServer;
import game.rise_of_valor.network.client.Client;
import game.rise_of_valor.network.server.GameServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.net.InetAddress;

import static game.rise_of_valor.shareData.DataManager.client;

public class StartServerViewControllers {
    GameServer gameServer ;

    @FXML
    Label serveInfo;
    @FXML
    public void startServer(ActionEvent actionEvent) {
        try {
            gameServer = new GameServer();
            gameServer.start();
            System.out.println("Server started successfully.");
            serveInfo.setText("Server is Running on IP: "+ gameServer.getIP() +" Port: "+ gameServer.getPort());
            client = new Client(InetAddress.getLocalHost().getHostAddress(), 5656);
            client.start();

            // Get the stage and add a close request handler
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setOnCloseRequest(this::handleWindowClose);
        } catch (Exception e) {
            System.err.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleWindowClose(WindowEvent event) {
        if (gameServer != null) {
            System.out.println("Shutting down server...");
            gameServer.interrupt();
            gameServer.shutdown();
        }
        if(client != null){
            System.out.println("Shutting down client...");
            client.shutdown();
        }
        System.exit(0);
//         Interrupt and stop all active threads
//        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
//        int noThreads = currentGroup.activeCount();
//        Thread[] lstThreads = new Thread[noThreads];
//        currentGroup.enumerate(lstThreads);
//
//        for (Thread thread : lstThreads) {
//            if (thread != null && thread != Thread.currentThread()) {
//                thread.interrupt();
//            }
//        }
    }



}
