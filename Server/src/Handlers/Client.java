package Handlers;

import Shared.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {

    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
