package Handlers;

import Shared.src.shared.Notification;
import Shared.src.shared.Request;
import Shared.src.shared.Response;
import Shared.src.shared.Response;

import Handlers.Handler;
import Shared.src.shared.Request.*;

import java.io.IOException;
import java.util.*;

/**
 * This class will apply the observer software design pattern
 * The purpose of this class is to generate observe for events that trigger a notification*/
public class NotificationHandler extends Handler {
    private final NotificationQueue notificationQueue = new NotificationQueue();;
    private Database db;
    private Handler nextHandler;
    public NotificationHandler(Database db, Handler nextHandler) {
        super(nextHandler);
        this.db = db;
        Thread notificationObserver = new Thread(new NotificationObserver());
        notificationObserver.start();
    }

    @Override
    protected void handleRequest(Request request, ClientHandler clientHandler) {
        Response response = request.getResponse();
        String msg = "invalid";
        String concernedUser;


        switch (response.RESPONSE_TYPE()) {
            case ADD_PRODUCT:
                List<String> concernedUsers = db.fetchInterestedUsers(request.getProductType());
                msg = "New product you might be interested in!";
                for (String s: concernedUsers
                ) {
                    notificationQueue.addNotification(new Notification(s, msg));
                }
                break;
            case SELL_PRODUCT:
                concernedUser = db.getBuyer(request.getOfferId());
                msg = "Seller has accepted your offer!!";
                notificationQueue.addNotification(new Notification(concernedUser, msg));
                break;
            case Make_Offer:
                concernedUser = db.getSeller(request.getProductId());
                msg = "New offer!!";
                notificationQueue.addNotification(new Notification(concernedUser, msg));
                break;
        }

    }


    private class NotificationQueue {
        private LinkedList<Notification> queue;

        public NotificationQueue() {
            queue = new LinkedList<>();
        }

        public void addNotification(Notification notification) {
            queue.addLast(notification);
            notifyAll();
        }

        public Notification getNotification() throws InterruptedException {
            if(queue.isEmpty()){
                wait();
            }
            return queue.getFirst();
        }
    }

    private class NotificationObserver implements Runnable{
        @Override
        public void run() {
            Notification notification = null;
            try {
                notification = notificationQueue.getNotification();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LinkedHashMap<Client, ClientHandler> onlineClients = ClientHandler.getOnlineClients();
            boolean online = false;
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
                db.addNotification(notification.getClientId(), notification.getMessage());
            }

        }
    }
}
