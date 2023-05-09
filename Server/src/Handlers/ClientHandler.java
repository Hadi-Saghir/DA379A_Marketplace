package Handlers;

import Shared.Notification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handles clients (incl. login /sign ups)*/
public class ClientHandler implements Runnable {
    private Client client;
    private ConcurrentLinkedQueue<Notification> buffer;

    public ClientHandler(Client client) {
        this.client = client;
        this.buffer = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        try (
                // Open input and output streams to the client socket
                ObjectOutputStream out = new ObjectOutputStream(client.getSocket().getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getSocket().getInputStream());
        ) {
            Object inputObject;
            while ((inputObject = in.readObject()) != null) {
                //call requestHandler
            }
            // Close the client socket
            client.getSocket().close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }


}
