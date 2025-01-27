package game.rise_of_valor.network.server;

import game.rise_of_valor.models.ClientData;

import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler {

    private final ObjectOutputStream outputStream;
  private final ClientData clientData;


    public ClientHandler( ObjectOutputStream outputStream, ClientData clientData) {

        this.outputStream = outputStream;
        this.clientData = clientData;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ClientData getClientData() {
        return clientData;
    }

}