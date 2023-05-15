package Handlers;

import Shared.Request;

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
        if(db.checkLogin(username,password)){
            return true;
        }
        return false;

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

