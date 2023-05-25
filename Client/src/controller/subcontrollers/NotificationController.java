package controller.subcontrollers;

import model.ResponseHandler;
import view.View;

/**
 * This class is responsible for passing notifications from the server to the view.
 */
public class NotificationController extends Thread {
    private final View view;
    private final ResponseHandler responseHandler;

    public NotificationController(View view, ResponseHandler responseHandler) {
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
