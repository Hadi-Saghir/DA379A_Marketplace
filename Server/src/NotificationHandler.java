import Handlers.Client;
import Handlers.ClientHandler;
import Handlers.RequestHandler;
import Shared.Notification;
import Shared.Request;

import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * This class will apply the observer software design pattern
 * The purpose of this class is to generate observe for events that trigger a notification*/
public class NotificationHandler implements Observer {
    private Map<Client, ClientHandler> onlineClients;
    private Queue<Notification> notifications;

    private RequestHandler requestHandler;

    public NotificationHandler(Map<Client, ClientHandler> onlineClients, RequestHandler requestHandler) {
        this.onlineClients = onlineClients;
        this.notifications = new ConcurrentLinkedQueue<>();
        this.requestHandler = requestHandler;
    }

    public void addNewClient(Client client, ClientHandler clientHandler){
        onlineClients.put(client, clientHandler);
    }

    public void removeClient(Client client){
        onlineClients.remove(client);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Notification) {
            Notification notification = (Notification) arg;
            addNotification(notification);
        }
    }

    public void processNotifications() {
        while (!notifications.isEmpty()) {
            Notification notification = notifications.poll();
            String clientId = notification.getClientId();
            String message = notification.getMessage();

            ClientHandler clientHandler;
            synchronized (onlineClients) {
                clientHandler = onlineClients.get(new Client(clientId));
            }

            if (clientHandler != null) {
                clientHandler.writeObject(new NotificationMessage(message));
            } else {
                // client not found, add notification to database
                requestHandler.handleRequest();
            }
        }
    }
}