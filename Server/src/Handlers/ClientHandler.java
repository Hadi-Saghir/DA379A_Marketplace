package Handlers;

import Shared.Notification;
import Shared.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;


/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handles clients (incl. login /sign ups)*/
public class ClientHandler implements Runnable {

    private static LinkedHashMap<Client, ClientHandler> onlineClients = new LinkedHashMap<>();

    private Client client;
    private RequestHandler requestHandler;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Client client, RequestHandler requestHandler) {
        this.client = client;
        this.requestHandler = requestHandler;
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
             ClientHandler.addOnlineClient(client, this); //set online

            Object inputObject;
            while ((inputObject = in.readObject()) != null) {
                if(inputObject instanceof Request){
                     requestHandler.handleRequest((Request) inputObject, this);

                     //Must add a response from the requestHandler. Response will be sent to the client
                }
                out.writeObject(new Object());
                out.flush();
            }
            // Close the client socket
            client.getSocket().close();
             ClientHandler.removeOnlineClient(client); //set online

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

    /**
     * The follow caller methods are to update information about online clients
     * client.getCurrClientId(): This method is called to get the current client ID.
     * client.loggedOut()/LoggedIn(): This method is called to update the client's logged-out status.
     * ClientHandler.updateClientId(): This method is called with the current client ID and
     *  the updated with the new Client ID.*/
    public void loggedIn(String userId) {
        ClientHandler.updateClientId(client.getCurrClientId(), client.loggedIn(userId));
    }
    public void loggedOut() {
        ClientHandler.updateClientId(client.getCurrClientId(), client.loggedOut());
    }


    //Static methods to handle online clients
    public synchronized static void updateClientId(String oldId, String newId) {

        for (Client client : onlineClients.keySet()) {
            if (client.getCurrClientId().equals(oldId)) {
                client.loggedIn(newId);
            }
        }
    }
    public synchronized static void addOnlineClient(Client client, ClientHandler clientHandler){
        onlineClients.put(client, clientHandler);
    }

    public synchronized static void removeOnlineClient(Client client){
        onlineClients.remove(client);
    }

    public synchronized static LinkedHashMap<Client, ClientHandler> getOnlineClients(){
        return onlineClients;
    }


}
