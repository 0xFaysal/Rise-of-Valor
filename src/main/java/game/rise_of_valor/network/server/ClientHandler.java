package game.rise_of_valor.network.server;

import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler {
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private String username;


    public ClientHandler(Socket socket, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AutoCloseable getSocket() {
        return socket;
    }
}