package Handlers;

import java.net.Socket;

public class Client {
    private Socket socket;
    private static int clientId = 0;
    private String currClientId;

    public Client(Socket socket) {
        this.socket = socket;
        currClientId = String.valueOf(clientId++);
    }

    public Socket getSocket() {
        return socket;
    }

    public String getCurrClientId() {
        return currClientId;
    }

    //Should solely be used when user logs on ().
    public void loggedIn(String userId) {
        this.currClientId = userId;
    }

    //Should solely be used when user logs out.
    public void loggedOut() {
        this.currClientId = String.valueOf(clientId++);
    }
}
