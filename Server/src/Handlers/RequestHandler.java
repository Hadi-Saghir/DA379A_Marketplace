package Handlers;

import Shared.Product;
import Shared.Request;
import Shared.User;

import java.util.List;

/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handles requests of purchase*/

public class RequestHandler {

    private DBHandler dbHandler;
    private User requester;

    public RequestHandler(DBHandler dbHandler, User requester){
        this.dbHandler =dbHandler;
        this.requester = requester;
    }

    public boolean registerUser(Request request) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String dob = request.getDateOfBirth().toString();
        String email = request.getEmail();
        String username = request.getUsername();
        String password = request.getPassword();
        return dbHandler.registerUser(firstName, lastName, dob, email, username, password);
    }

    public User loginUser(Request request) {
        String username = request.getUsername();
        String password = request.getPassword();
        return dbHandler.loginUser(username, password);
    }

    public boolean addProduct(Request request) {
        User seller = requester;
        String type = request.getType().toString();
        double price = request.getPrice();
        int year = request.getYearOfProduction();
        String color = request.getColor();
        String condition = request.getCondition();
        return dbHandler.addProduct(seller, type, price, year, color, condition);
    }

    public List<Product> searchProducts(Request request) {
        String type = request.getType().toString();
        double minPrice = request.getMinPrice();
        double maxPrice = request.getMaxPrice();
        String condition = request.getCondition();
        return dbHandler.searchProducts(type, minPrice, maxPrice, condition);
    }

    public boolean buyProduct(Request request) {
        User buyer = requester;
        int product = request.getProductId();
        return dbHandler.buyProduct(buyer, product);
    }

    public boolean makeOffer(Request request) {
        User seller = requester;
        int offer = request.getOfferId();
        return dbHandler.makeOffer(seller, offer);
    }

    public List<Product> getPurchases(Request request) {
        User buyer = requester;
        String startDate = request.getStartDate().toString();
        String endDate = request.getEndDate().toString();
        return dbHandler.getPurchases(buyer, startDate, endDate);
    }

    public boolean registerInterest(Request request) {
        User user = requester;
        String type = request.getType().toString();
        return dbHandler.registerInterest(user, type);
    }

    public List<Product> getNotifications(Request request) {
        User user = requester;
        return dbHandler.getNotifications(user);
    }
}
