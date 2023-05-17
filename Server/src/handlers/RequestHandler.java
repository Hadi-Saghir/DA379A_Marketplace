package handlers;

import shared.Request;
import shared.Notification;
import shared.Product;
import shared.Response;

import java.util.List;

/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handle requests of purchase*/

public class RequestHandler extends Handler {

    private final Database database;
    protected Handler nextHandler;
    public RequestHandler(Database database, Handler nextHandler){
        super(nextHandler);
        this.database = database;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }


    @Override
    public void handleRequest(Request request, ClientHandler clientHandler) {
        switch (request.getType().toString()) {
            case "REGISTER" -> registerUser(request, clientHandler);
            case "LOGIN" -> loginUser(request, clientHandler);
            case "ADD_PRODUCT" -> addProduct(request, clientHandler);
            case "SEARCH_PRODUCT" -> searchProducts(request, clientHandler);
            case "SELL_PRODUCT" -> sellProduct(request, clientHandler);
            case "MAKE_OFFER" -> makeOffer(request, clientHandler);
            case "REGISTER_INTEREST" -> registerInterest(request, clientHandler);
            case "GET_PURCHASE_HISTORY" -> getPurchases(request, clientHandler);
            default -> {
            }
        }
    }


    public Response registerUser(Request request, ClientHandler requester) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String dob = request.getDateOfBirth().toString();
        String email = request.getEmail();
        String username = request.getUsername();
        String password = request.getPassword();

        return database.registerUser(firstName, lastName, dob, email, username, password);
    }

    public Response addProduct(Request request, ClientHandler requester) {
        String seller = String.valueOf(request.getUserId());
        String type = request.getType().toString();
        double price = request.getPrice();
        int year = request.getYearOfProduction();
        String color = request.getColor();
        String condition = request.getCondition();
        return database.addProduct(seller, type, price, year, color, condition);
    }

    public Response loginUser(Request request, ClientHandler requester) {
        String username = request.getUsername();
        String password = request.getPassword();
        return database.loginUser(username, password);
    }

    public List<Product> searchProducts(Request request, ClientHandler requester) {
        String type = request.getType().toString();
        double minPrice = request.getMinPrice();
        double maxPrice = request.getMaxPrice();
        String condition = request.getCondition();
        return database.searchProducts(type, minPrice, maxPrice, condition);
    }

    public Response sellProduct(Request request, ClientHandler requester) {
        int seller = request.getUserId();
        int offerId = request.getOfferId();
        return database.sellProduct(seller, offerId);
    }

    public Response makeOffer(Request request, ClientHandler requester) {
        int seller = request.getUserId();
        int offer = request.getOfferId();
        return database.makeOffer(seller, offer);
    }

    public Response getPurchases(Request request, ClientHandler requester) {
        int buyer = request.getUserId();
        String startDate = request.getStartDate().toString();
        String endDate = request.getEndDate().toString();
        return database.getPurchases(buyer, startDate, endDate);
    }

    public Response registerInterest(Request request, ClientHandler requester) {
        int buyer = request.getUserId();
        String type = request.getType().toString();
        return database.registerInterest(buyer, type);
    }

    public Response getNotifications(Request request) {
        int user = request.getUserId();
        return database.getNotifications(user);
    }

    public synchronized boolean addNotifications(String clientId, String message) {
        return database.addNotification(clientId, message);
    }



}
