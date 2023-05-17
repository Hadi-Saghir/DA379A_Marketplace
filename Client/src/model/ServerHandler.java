package model;

import controller.subcontrollers.ConnectionController;
import shared.Request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerHandler {
    private final String HOST;
    private final int PORT;
    private final ConnectionController connectionController;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ServerHandler(String host, int port, ConnectionController connectionController) {
        this.HOST = host;
        this.PORT = port;
        this.connectionController = connectionController;
    }

    public void connectToServer() throws IOException {
        if(socket == null) {
            socket = new Socket(HOST, PORT);
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }
    }

    public void disconnectFromServer() throws IOException {
        if(socket != null) {
            out.flush();
            socket.close();
        }
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }
}
