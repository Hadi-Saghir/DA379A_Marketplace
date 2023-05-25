package model;

import controller.MainController;
import shared.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResponseHandler extends Thread {
    private final ObjectInputStream in;
    private AtomicBoolean running = new AtomicBoolean(true);
    private final LinkedList<Response> responses = new LinkedList<>();

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

    public void stopRunning() {
        running.set(false);
    }

    public synchronized Response getResponse() {
        while(responses.isEmpty()) {
            try {
                wait();
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return responses.pop();
    }

    public synchronized void addResponse(Response response) {
        responses.addLast(response);
        notifyAll();
    }
}
