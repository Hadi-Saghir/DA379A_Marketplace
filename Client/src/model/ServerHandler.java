package model;

import controller.subcontrollers.ConnectionController;
import shared.Request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ServerHandler is a class that handles the connection to the server.
 */
public class ServerHandler {
    private final String HOST;
    private final int PORT;
    private final ConnectionController connectionController;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Constructor for ServerHandler.
     * @param host The host to connect to.
     * @param port The port to connect to.
     * @param connectionController The connectionController of the client.
     */
    public ServerHandler(String host, int port, ConnectionController connectionController) {
        this.HOST = host;
        this.PORT = port;
        this.connectionController = connectionController;
    }

    /**
     * Connects to the server if there is no connection.
     * Saves the input and output streams.
     * @throws IOException If the connection fails.
     */
    public void connectToServer() throws IOException {
        if(socket == null) {
            socket = new Socket(HOST, PORT);
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }
    }

    /**
     * Disconnects from the server if there is a connection.
     * @throws IOException If the disconnection fails.
     */
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
