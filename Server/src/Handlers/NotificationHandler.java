package Handlers;

import Shared.Notification;

import java.io.IOException;
import java.util.*;


/**
 * This class will apply the observer software design pattern
 * The purpose of this class is to generate observe for events that trigger a notification*/
public class NotificationHandler{
    private NotificationQueue notificationQueue;

    public NotificationHandler() {
        notificationQueue = new NotificationQueue();
        notificationQueue.addObserver(new NotificationObserver());
    }


    private class NotificationQueue extends Observable {
        private LinkedList<Notification> queue;

        public NotificationQueue() {
            queue = new LinkedList<>();
        }

        public void addNotification(Notification notification) {
            queue.add(notification);
            setChanged();
            notifyObservers(notification);
        }
    }

    private class NotificationObserver implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof Notification) {
                Notification notification = (Notification) arg;
                LinkedHashMap<Client, ClientHandler> onlineClients = ClientHandler.getOnlineClients();
                Boolean online = false;
                for(Client client: onlineClients.keySet()){
                    if(client.getCurrClientId().equals(notification.getClientId())){
                        try {
                            onlineClients.get(client).writeToClient(notification);
                            online = true;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if(!online){

                }
            }
        }
    }
}
