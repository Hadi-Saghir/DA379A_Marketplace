package handlers;

import Shared.src.shared.Request;
import Shared.src.shared.Response;

public abstract class Handler {
    private Handler nextHandler;

    private Request request;
    private Response response;

    public Handler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    private boolean continueToNextHandler = true;
    public void stopHandling(){ continueToNextHandler = false;}

    public void handle(Request request, ClientHandler clientHandler) {

        this.request = request;
        handleRequest(request, clientHandler);

        if (this.nextHandler != null && continueToNextHandler) {
            nextHandler.handle(request, clientHandler);
        }
    }

    protected abstract void handleRequest(Request request, ClientHandler clientHandler);

    public void setResponse(Response response){
        request.setResponse(response);
    }


}

