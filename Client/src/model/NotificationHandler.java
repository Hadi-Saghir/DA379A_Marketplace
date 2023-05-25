package model;

import model.ResponseHandler;
import view.View;

public class NotificationHandler extends Thread {
    private final View view;
    private final ResponseHandler responseHandler;

    public NotificationHandler(View view, ResponseHandler responseHandler) {
        this.view = view;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        while(true) {
            view.showNotification(responseHandler.getNotification().getMessage());
        }
    }
}
