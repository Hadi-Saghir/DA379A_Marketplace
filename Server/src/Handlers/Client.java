package Handlers;

import java.net.Socket;

public class Client {
    private Socket socket;
    private static int clientId = 0;
    private int currClientId;

    public Client(Socket socket) {
        this.socket = socket;
        currClientId = clientId++;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getCurrClientId() {
        return currClientId;
    }
}
