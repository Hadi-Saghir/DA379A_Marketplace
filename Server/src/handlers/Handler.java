package Handlers;

import Shared.src.shared.Request;
import Shared.src.shared.Response;

public abstract class Handler {
    private Handler nextHandler;

    private Request request;
    private Shared.src.shared.Response response;

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

        this.request = request;
        handleRequest(request, clientHandler);

        if (this.nextHandler != null) {

            nextHandler.handle(request, clientHandler);
        }
    }

    protected abstract void handleRequest(Request request, ClientHandler clientHandler);

    public void setResponse(Response response){
        request.setResponse(response);
    }


}

