package controller;

import shared.Request;
import shared.Response;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;

public class ConnectionController {
    private final MainController mainController;
    private final String HOST;
    private final int PORT;
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public ConnectionController(MainController mainController, String host, int port) {
        this.mainController = mainController;
        this.HOST = host;
        this.PORT = port;
    }

    private void connectToServer() {
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

    protected void disconnectFromServer() {
        if(socket != null) {
            try {
                reader.close();
                writer.close();
                socket.close();
                System.out.println("Disconnected from server");
            } catch (Exception e) {
                System.out.println("Error disconnecting from server");
                System.out.println(e.getMessage());
            }
        }
    }

    public void sendRequest(Request request) {
        connectToServer();

        try {
            writer.writeObject(request);
            Response response = (Response) reader.readObject();
            mainController.showMessage(response.MESSAGE());
            mainController.onLoginSuccess();
        } catch(IOException ex) {
            mainController.showError("Error sending request to server. Please try again.");
            mainController.showError(ex.getMessage());
        } catch(ClassNotFoundException ex) {
            mainController.showError("Error reading response from server. Please try again.");
            mainController.showError(ex.getMessage());
        }
    }

    public void doLogin(String username, String password) {
        Request request = Request.login(username, password);
        sendRequest(request);
    }


    public void doCreateNewUser(String firstName, String lastName, LocalDate dateOfBirth, String email, String username, String password) {
        Request request = Request.register(firstName, lastName, dateOfBirth, email, username, password);
        sendRequest(request);
    }
}
