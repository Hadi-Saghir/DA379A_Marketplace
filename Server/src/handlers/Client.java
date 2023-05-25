package handlers;

import java.net.Socket;

public class Client {
    private Socket socket;
    private String currClientId = "";


    public Client(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getCurrClientId() {
        return currClientId;
    }

    //Should solely be used when user logs on ().
    public String loggedIn(String userId) {
        this.currClientId = userId;
        return this.currClientId;
    }

    //Should solely be used when user logs out.
    public String loggedOut() {
        this.currClientId = "";
        return this.currClientId;
    }

    public boolean isLoggedIn() {
        return !currClientId.equals("");
    }
}
