package Handlers;

import Shared.Notification;
import Shared.Product;
import Shared.Request;
import Shared.Response.ResponseType;

import java.util.List;

/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handle requests of purchase*/

public class RequestHandler extends Handler {

    private Database database;
    protected Handler nextHandler;
    public RequestHandler(Database database, Handler nextHandler){
        super(nextHandler);
        this.database = database;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }


    @Override
    public void handleRequest(Request request, ClientHandler requester) {
        switch (request.getType().toString()) {
            case "REGISTER":
                registerUser(request, requester);
                break;
            case "LOGIN":
                loginUser(request, requester);
                break;
            case "ADD_PRODUCT":
                addProduct(request, requester);
                break;
            case "SEARCH_PRODUCT":
                searchProducts(request, requester);
                break;
            case "SELL_PRODUCT":
                sellProduct(request, requester);
                break;
            case "MAKE_OFFER":
                makeOffer(request, requester);
                break;
            case "REGISTER_INTEREST":
                registerInterest(request, requester);
                break;
            case "GET_PURCHASE_HISTORY":
                getPurchases(request, requester);
                break;
            default:
                break;
        }
    }


    public ResponseType registerUser(Request request, ClientHandler requester) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String dob = request.getDateOfBirth().toString();
        String email = request.getEmail();
        String username = request.getUsername();
        String password = request.getPassword();

        ResponseType res = database.registerUser(firstName, lastName, dob, email, username, password);
        if(!res.equals(ResponseType.SUCCESS)){
            requester.loggedIn(username);
            return res;
        }
        return res;
    }

    public ResponseType addProduct(Request request, ClientHandler requester) {
        String seller = String.valueOf(request.getUsername());
        String type = request.getType().toString();
        double price = request.getPrice();
        int year = request.getYearOfProduction();
        String color = request.getColor();
        String condition = request.getCondition();
        return database.addProduct(seller, type, price, year, color, condition);
    }

    public ResponseType loginUser(Request request, ClientHandler requester) {
        String username = request.getUsername();
        String password = request.getPassword();
        ResponseType res = database.loginUser(username, password);
        if(!res.equals("userId")){
            requester.loggedIn(username);
            return res;
        }
        return res;
    }

    public List<Product> searchProducts(Request request, ClientHandler requester) {
        String type = request.getType().toString();
        double minPrice = request.getMinPrice();
        double maxPrice = request.getMaxPrice();
        String condition = request.getCondition();
        return database.searchProducts(type, minPrice, maxPrice, condition);
    }

    public ResponseType sellProduct(Request request, ClientHandler requester) {
        String seller = request.getUsername();
        int offerId = request.getOfferId();
        return database.sellProduct(seller, offerId);
    }

    public ResponseType makeOffer(Request request, ClientHandler requester) {
        String seller = request.getUsername();
        int offer = request.getOfferId();
        return database.makeOffer(seller, offer);
    }

    public List<Product> getPurchases(Request request, ClientHandler requester) {
        String buyer = request.getUsername();
        String startDate = request.getStartDate().toString();
        String endDate = request.getEndDate().toString();
        return database.getPurchases(buyer, startDate, endDate);
    }

    public boolean registerInterest(Request request, ClientHandler requester) {
        String buyer = request.getUsername();
        String type = request.getType().toString();
        return database.registerInterest(buyer, type);
    }

    public List<Notification> getNotifications(Request request) {
        String user = request.getUsername();
        return database.getNotifications(user);
    }

    public synchronized ResponseType addNotifications(String clientId, String message) {
        return database.addNotification(clientId, message);
    }





}
