package model;

import controller.MainController;
import shared.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResponseHandler extends Thread {
    private final ObjectInputStream in;
    private final MainController mainController;
    private AtomicBoolean running = new AtomicBoolean(true);

    public ResponseHandler(MainController mainController, ObjectInputStream in) {
        this.in = in;
        this.mainController = mainController;
    }


    @Override
    public void run() {
        while(running.get()) {
            try {
                Object response = in.readObject();

                if(response instanceof Response) {
                    mainController.handleResponse((Response) response);
                } else {
                    throw new IOException();
                }
            } catch(IOException | ClassNotFoundException e) {
                System.out.println("bla bla"); //FIXME
            }
        }
    }

    public void stopRunning() {
        running.set(false);
    }
}
