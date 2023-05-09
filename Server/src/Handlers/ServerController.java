package Handlers;

import Handlers.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerController implements Runnable{

    private ServerSocket serverSocket;
    private DBHandler dbHandler;
    private RequestHandler requestHandler;
    private static LinkedHashMap<Client, ClientHandler> onlineClients;
    private ExecutorService executorService;
    private AtomicBoolean running;

    public ServerController(int port) throws IOException {
        System.out.println("Server starting");
        //Server
        serverSocket = new ServerSocket(port);
        running = new AtomicBoolean(true);

        // Client thread pool and requestHandler
        executorService = Executors.newFixedThreadPool(10);
        dbHandler = new DBHandler();
        requestHandler = new RequestHandler(dbHandler);

    }

    //kill switch
    public void stop() throws IOException {
        System.out.println("Server stopping");
        running.set(false);
        executorService.shutdown();
        serverSocket.close();
    }

    @Override
    public void run() {
        System.out.println("Server started");
        while (running.get()) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected.");
                Client client = new Client(socket);
                // Submit a task to the thread pool to handle the client request
                ClientHandler clientHandler = new ClientHandler(client, requestHandler);
                executorService.submit(clientHandler);
            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e.getMessage());
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
