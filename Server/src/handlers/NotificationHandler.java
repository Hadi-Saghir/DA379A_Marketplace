package handlers;

import java.io.IOException;
import java.util.*;


/**
 * This class will apply the observer software design pattern
 * The purpose of this class is to generate observe for events that trigger a notification*/
public class NotificationHandler extends Handler{
    private NotificationQueue notificationQueue;
    private Database db;
    private Handler nextHandler;
    public NotificationHandler(Database db, Handler nextHandler) {
        super(nextHandler);
        this.db = db;
        notificationQueue = new NotificationQueue();
        notificationQueue.addObserver(new NotificationObserver());
    }

    @Override
    protected void handleRequest(Request request, ClientHandler clientHandler) {
        Response response = request.getResponse();
        String msg = "invalid";


        switch (response.RESPONSE_TYPE()) {
            case ADD_PRODUCT:
                List<String> concernedUsers = db.fetchInterestedUsers(request.getProductType());

                for (String s: concernedUsers
                ) {
                    notificationQueue.addNotification(new Notification(s, msg));
                }
                msg = "New product you might be interested in!";
                break;
            case SELL_PRODUCT:
                String concernedUser = db.getBuyer(request.getOfferId());
                msg = "Seller has accepted your offer!!";
                notificationQueue.addNotification(new Notification(s, msg));
                break;
            case Make_Offer:
               String concernedUser = db.getSeller(request.getProductId());
                msg = "New offer!!";
                notificationQueue.addNotification(new Notification(s, msg));
                break;
        }

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
}
