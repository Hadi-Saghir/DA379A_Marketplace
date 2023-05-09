import Handlers.Client;
import Handlers.ClientHandler;
import Shared.Notification;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;


/**
 * This class will apply the observer software design pattern
 * The purpose of this class is to generate observe for events that trigger a notification*/
public class NotificationHandler implements Observer {
    private Map<Client, ClientHandler> outputStreams;

    public NotificationHandler(Map<Client, ClientHandler> outputStreams) {
        this.outputStreams = outputStreams;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Notification) {
            Notification notification = (Notification) arg;
            String clientId = notification.getClientId();
            String message = notification.getMessage();

            ObjectOutputStream outputStream;
            synchronized (outputStreams) {
                outputStream = outputStreams.get(clientId);
            }

            if (outputStream != null) {
                // lock the output stream while writing to it
                synchronized (outputStream) {
                    try {
                        outputStream.writeObject(new NotificationMessage(message));
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // client not found, add notification to database
                DBHandler.addNotification(clientId, message);
            }
        }
    }
}
