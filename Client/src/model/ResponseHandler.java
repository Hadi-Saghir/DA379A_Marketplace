package model;

import shared.Notification;
import shared.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private void addNotification(Notification response) {
        synchronized(notifications) {
            notifications.addLast(response);
            notifications.notifyAll();
        }
    }

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

    public synchronized void addResponse(Response response) {
        synchronized(responses) {
            responses.addLast(response);
            responses.notifyAll();
        }
    }
}
