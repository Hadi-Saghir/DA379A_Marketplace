package model;

import shared.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestHandler extends Thread {
    private final ObjectOutputStream out;
    private AtomicBoolean running = new AtomicBoolean(true);
    private final LinkedList<Request> requestsBuffer = new LinkedList<>();

    public RequestHandler(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        while(running.get()) {
            synchronized(requestsBuffer) {
                if(requestsBuffer.isEmpty()) {
                    try {
                        requestsBuffer.wait();
                    } catch(InterruptedException ignored) {}
                }

                try {
                    out.writeObject(requestsBuffer.pop());
                    out.flush();
                } catch(IOException e) {
                    System.out.println("bla bla failed"); //FIXME
                }
            }
        }
    }

    public void stopRunning() {
        running.set(false);
    }

    public void sendRequest(Request request) {
        synchronized(requestsBuffer) {
            requestsBuffer.addLast(request);
            requestsBuffer.notify();
        }
    }
}
