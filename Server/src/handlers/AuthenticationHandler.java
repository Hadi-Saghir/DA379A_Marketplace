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


    @Override
    protected void handleRequest(Request request, ClientHandler clientHandler) {

        if(request.getType() == Request.RequestType.LOGIN) {
            if (!authenticate(request.getUsername(), request.getPassword())) {
                // enter here if cannot authenticate
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

            clientHandler.loggedIn(request.getUsername());

        }

}
}

