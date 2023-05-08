package Handlers;

import Shared.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handles clients (incl. login /sign ups)*/
public class ClientHandler implements Runnable {

    private ClientBuffer clientBuffer;

    public ClientHandler(ClientBuffer clientBuffer) {
        this.clientBuffer = clientBuffer;
    }

    @Override
    public void run() {
        // Get a client from the buffer
        Client client = null;
        try {
            client = clientBuffer.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try (
                // Open input and output streams to the client socket
                ObjectOutputStream out = new ObjectOutputStream(client.getSocket().getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getSocket().getInputStream());
        ) {
            Object inputObject;
            while ((inputObject = in.readObject()) != null) {
                // Echo back the input to the client
                out.writeObject("Server: " + inputObject.toString());
            }
            // Close the client socket
            client.getSocket().close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}
