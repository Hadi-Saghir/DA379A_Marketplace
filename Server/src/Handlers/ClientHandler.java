package Handlers;

import Shared.Notification;
import Shared.Request;

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
    private RequestHandler requestHandler;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Client client, RequestHandler requestHandler) {
        this.client = client;
        this.requestHandler = requestHandler;
        this.buffer = new ConcurrentLinkedQueue<>();
        try {
            out = new ObjectOutputStream(client.getSocket().getOutputStream());
            in = new ObjectInputStream(client.getSocket().getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
         try {
            Object inputObject;
            while ((inputObject = in.readObject()) != null) {
                if(inputObject instanceof Request){
                     requestHandler.handleRequest((Request) inputObject, client);

                     //Must add a response from the requestHandler. Response will be sent to the client
                }
                out.writeObject(new Object());
                out.flush();
            }
            // Close the client socket
            client.getSocket().close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    public void writeToClient(Object object) throws IOException {
        synchronized (out){
            out.writeObject(object);
            out.flush();
        }
    }


}
