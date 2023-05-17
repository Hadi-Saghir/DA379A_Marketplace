package handlers;

import Shared.Shared.src.Request;

public abstract class Handler {
    private Handler nextHandler;

    public Handler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public Handler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }


    public void handle(Request request, ClientHandler clientHandler) {

        handleRequest(request, clientHandler);

        if (this.nextHandler != null) {

            nextHandler.handle(request, clientHandler);
        }
    }

    protected abstract void handleRequest(Request request, ClientHandler clientHandler);
}

