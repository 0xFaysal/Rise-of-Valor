package game.rise_of_valor.network.server;

import game.rise_of_valor.models.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static game.rise_of_valor.network.server.GameServerThread.clients;
import static game.rise_of_valor.network.server.GameServerThread.sentToAll;
//import static game.rise_of_valor.network.server.GameServerThread.objectOutputStream;

public class ServerReceverThread extends Thread {
    private final ObjectInputStream objectInputStream;
    private final ServerSenderThread serverSenderThread;
    private final ObjectOutputStream objectOutputStream;

    public ServerReceverThread(ObjectInputStream objectInputStream, ServerSenderThread serverSenderThread, ObjectOutputStream objectOutputStream) {
        this.objectInputStream = objectInputStream;
        this.serverSenderThread = serverSenderThread;
        this.objectOutputStream = objectOutputStream;
        this.setName("ServerReceverThread");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) objectInputStream.readObject();
                switch (message.getMode()) {
                    case "connection":
                        message.setConnectionSuccessful(true);
                        System.out.println("Received connection message: " + message);
                        serverSenderThread.sendToClients(message);
                        break;
                    case "createAccount":
                        System.out.println("Received initial message: " + message);
                        clients.add(new ClientHandler(objectOutputStream, message.getClientData()));
//                        sentToAll(message, message.getClientData().getUsername());
                        break;
                    case "setuser":
                        System.out.println("Received setuser message: " + message);
                        break;
                    case "createdue":
                        System.out.println("Received createdue message: " + message);
                        break;
                    case "communication":
                        System.out.println("Received communication message: " + message);
                        sentToAll(message, message.getClientData().getUsername());

                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break; // Exit the loop on exception
            }
        }
    }
}