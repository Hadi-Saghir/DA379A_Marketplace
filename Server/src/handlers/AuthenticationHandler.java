package handlers;


import Shared.src.shared.Request;
import Shared.src.shared.Response;

import java.io.IOException;


public class AuthenticationHandler extends Handler {

    private Database db;

    public AuthenticationHandler(Database db, Handler nextHandler) {
        super(nextHandler);
        System.out.println("Next Handler form Auth " + nextHandler );
        this.db = db;

    }

    public boolean authenticate(String username, String password){
        return db.checkLogin(username, password);

    }

    //Is user already logged?
        //Yes: Do nothing
        //No: Is  username and password correct?
            //Yes: set isLogged to true [clientHandler.isLoggedIn()]
            //No: Write Response.ResponseResult.FAILURE to stream and stop handling
    @Override
    protected void handleRequest(Request request, ClientHandler clientHandler) {

        if(request.getType() == Request.RequestType.LOGIN) {
            if (!authenticate(request.getUsername(), request.getPassword())) {
                try {
                    stopHandling();
                    clientHandler.writeToClient(new Response(Response.ResponseType.LOGIN , Response.ResponseResult.FAILURE, null));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                clientHandler.loggedIn(request.getUsername());
            }
        } else if(!clientHandler.isLoggedIn() && request.getType() != Request.RequestType.REGISTER) {
            try {
                stopHandling();
                clientHandler.writeToClient(new Response(Response.ResponseType.LOGIN , Response.ResponseResult.FAILURE, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
}

