package model;

import shared.Notification;
import shared.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is responsible for handling responses and notifications from the server.
 * It is a thread that runs in the background and waits for responses.
 * <p>
 * When a response is received, it is added to a queue, acting as a producer.
 * <p>
 * It has two queues, one for responses and one for notifications.
 */
public class ResponseHandler extends Thread {
    private final ObjectInputStream in;
    private AtomicBoolean running = new AtomicBoolean(true);
    private final LinkedList<Response> responses = new LinkedList<>();
    private final LinkedList<Notification> notifications = new LinkedList<>();

    public ResponseHandler(ObjectInputStream in) {
        this.in = in;
    }


    @Override
    public void run() {
        while(running.get()) {
            try {
                Object response = in.readObject();

                if(response instanceof Response) {
                    addResponse((Response) response);
                } else if(response instanceof Notification) {
                    addNotification((Notification) response);
                } else {
                    throw new IOException();
                }
            } catch(IOException | ClassNotFoundException e) {
                System.out.println("bla bla"); //FIXME
                running.set(false);
            } catch(Exception e) {
                System.err.println("Annat fel?");
            }
        }
    }

    /**
     * Adds a notification to the queue.
     * @param response The notification from the server.
     */
    private void addNotification(Notification response) {
        synchronized(notifications) {
            notifications.addLast(response);
            notifications.notifyAll();
        }
    }

    /**
     * Returns the next notification in the queue.
     * If the queue is empty, the thread will wait until a notification is added.
     * @return The next notification in the queue.
     */
    public Notification getNotification() {
        synchronized(notifications) {
            while(notifications.isEmpty()) {
                try {
                    notifications.wait();
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return notifications.pop();
        }
    }

    public void stopRunning() {
        running.set(false);
    }

    /**
     * Returns the next response in the queue.
     * If the queue is empty, the thread will wait until a response is added.
     * @return The next response in the queue.
     */
    public Response getResponse() {
        synchronized(responses) {
            while(responses.isEmpty()) {
                try {
                    responses.wait();
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return responses.pop();
        }
    }

    /**
     * Adds a response to the queue.
     * @param response The response from the server.
     */
    public synchronized void addResponse(Response response) {
        synchronized(responses) {
            responses.addLast(response);
            responses.notifyAll();
        }
    }
}
