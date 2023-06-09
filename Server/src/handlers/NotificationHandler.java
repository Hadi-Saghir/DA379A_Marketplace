package handlers;

import Shared.src.shared.Notification;
import Shared.src.shared.Request;
import Shared.src.shared.Response;


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
            case ADD_PRODUCT -> {
                List<String> concernedUsers = db.fetchInterestedUsers(String.valueOf(request.getProductType()));
                msg = "New product you might be interested in!";
                for (String s : concernedUsers
                ) {
                    notificationQueue.addNotification(new Notification(s, msg));
                }
            }
            case SELL_PRODUCT -> {
                concernedUser = db.getBuyer(request.getOfferId());
                msg = "Seller has accepted your offer!!";
                notificationQueue.addNotification(new Notification(concernedUser, msg));
            }
            case MAKE_OFFER -> {
                concernedUser = db.getSeller(request.getProductId());
                msg = "New offer!!";
                notificationQueue.addNotification(new Notification(concernedUser, msg));
            }
        }

    }


    private class NotificationQueue {
        private LinkedList<Notification> queue;
        private boolean newNotification;

        public NotificationQueue() {
            queue = new LinkedList<>();
            newNotification = false;
        }

        public synchronized void addNotification(Notification notification) {
            queue.addLast(notification);
            newNotification = true;
            notifyAll();
        }

        public synchronized Notification getNotification() throws InterruptedException {
            while (!newNotification) {
                wait();
            }
            newNotification = false;
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
