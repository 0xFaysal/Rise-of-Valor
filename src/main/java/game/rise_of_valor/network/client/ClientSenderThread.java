package game.rise_of_valor.network.client;

import game.rise_of_valor.models.ClientData;
import game.rise_of_valor.models.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

class ClientSenderThread extends Thread {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;

    public ClientSenderThread(Socket socket) throws IOException {
        this.socket = socket;
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.setName("ClientSenderThread");
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your username: ");
            String username = "username";

            // Send username to server
            objectOutputStream.writeObject(new Message(username));
            objectOutputStream.flush();

            while (socket.isConnected()) {
                System.out.print("Send message to:");
                String receiver = scanner.nextLine();
                System.out.print("Enter your message: ");
                String messageText = scanner.nextLine();

                Message message = new Message(username, messageText, receiver);
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDueModeRoom(ClientData clientData) {
        try {
            objectOutputStream.writeObject(new Message("createdue", clientData));
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}