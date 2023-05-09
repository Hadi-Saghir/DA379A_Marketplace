package Handlers;

import Shared.Notification;
import Shared.Product;
import Shared.Request;

import java.util.List;

/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handle requests of purchase*/

public class RequestHandler {

    private DBHandler dbHandler;

    public RequestHandler(DBHandler dbHandler){
        this.dbHandler = dbHandler;
    }

    public synchronized void handleRequest(Request request, ClientHandler requester) {
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


    public String registerUser(Request request, ClientHandler requester) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String dob = request.getDateOfBirth().toString();
        String email = request.getEmail();
        String username = request.getUsername();
        String password = request.getPassword();

        String res = dbHandler.registerUser(firstName, lastName, dob, email, username, password);
        if(!res.equals("userId")){
            requester.loggedIn(res);
        }
        return res;
    }

    public String loginUser(Request request, ClientHandler requester) {
        String username = request.getUsername();
        String password = request.getPassword();
        String res = dbHandler.loginUser(username, password);
        if(!res.equals("userId")){
            requester.loggedIn(res);
        }
        return res;
    }

    public boolean addProduct(Request request, ClientHandler requester) {
        String seller = String.valueOf(request.getUserId());
        String type = request.getType().toString();
        double price = request.getPrice();
        int year = request.getYearOfProduction();
        String color = request.getColor();
        String condition = request.getCondition();
        return dbHandler.addProduct(seller, type, price, year, color, condition);
    }

    public List<Product> searchProducts(Request request, ClientHandler requester) {
        String type = request.getType().toString();
        double minPrice = request.getMinPrice();
        double maxPrice = request.getMaxPrice();
        String condition = request.getCondition();
        return dbHandler.searchProducts(type, minPrice, maxPrice, condition);
    }

    public boolean sellProduct(Request request, ClientHandler requester) {
        int seller = request.getUserId();
        int offerId = request.getOfferId();
        return dbHandler.sellProduct(seller, offerId);
    }

    public boolean makeOffer(Request request, ClientHandler requester) {
        int seller = request.getUserId();
        int offer = request.getOfferId();
        return dbHandler.makeOffer(seller, offer);
    }

    public List<Product> getPurchases(Request request, ClientHandler requester) {
        int buyer = request.getUserId();
        String startDate = request.getStartDate().toString();
        String endDate = request.getEndDate().toString();
        return dbHandler.getPurchases(buyer, startDate, endDate);
    }

    public boolean registerInterest(Request request, ClientHandler requester) {
        int buyer = request.getUserId();
        String type = request.getType().toString();
        return dbHandler.registerInterest(buyer, type);
    }

    public List<Notification> getNotifications(Request request) {
        int user = request.getUserId();
        return dbHandler.getNotifications(user);
    }

    public synchronized boolean addNotifications(String clientId, String message) {
        return dbHandler.addNotification(clientId, message);
    }
}
