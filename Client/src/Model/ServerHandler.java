package model;

import controller.subcontrollers.ConnectionController;
import Shared.Shared.src.Request;
import Shared.Shared.src.Response;

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
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public ServerHandler(String host, int port, ConnectionController connectionController) {
        this.HOST = host;
        this.PORT = port;
        this.connectionController = connectionController;
    }

    public void connectToServer() {
        if(socket == null) {
            try {
                socket = new Socket(HOST, PORT);
                reader = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                writer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                System.out.println("Connected to server");
            } catch (Exception e) {
                System.out.println("Error connecting to server");
                System.out.println(e.getMessage());
            }
        }
    }

    public void disconnectFromServer() {
        if(socket != null) {
            try {
                reader.close();
                writer.close();
                socket.close();
                connectionController.showMessage("Disconnected from server");
            } catch (Exception e) {
                connectionController.showError("Error disconnecting from server");
                connectionController.showMessage(e.getMessage());
            }
        }
    }

    public Response sendRequest(Request request) throws IOException, ClassNotFoundException {
        connectToServer();
        writer.writeObject(request);
        return (Response) reader.readObject();
    }


}
