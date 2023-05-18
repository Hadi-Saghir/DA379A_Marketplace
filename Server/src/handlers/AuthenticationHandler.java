package Handlers;


import shared.Request;


public class AuthenticationHandler extends Handler {

    protected Handler nextHandler;
    private Database db;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public AuthenticationHandler(Database db, Handler nextHandler) {
        super(nextHandler);
        this.db = db;

    }

    public boolean authenticate(String username, String password){
        return db.checkLogin(username, password);

    }

    @Override
    protected void handleRequest(Request request, ClientHandler clientHandler) {
        if(request.getType() == Request.RequestType.LOGIN){
            authenticate(request.getUsername(),request.getPassword());
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request,clientHandler);
        }
    }
}

