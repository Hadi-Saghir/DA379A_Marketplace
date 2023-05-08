package Handlers;

import Shared.Product;
import Shared.User;

import java.util.List;


/**
 * This class will is part of the application of the chain of responsibility software design pattern
 * The purpose of this class is to handles queries to the database*/
public class DBHandler {

    public boolean registerUser(String firstName, String lastName, String dob, String email, String username, String password) {
        return false;
    }

    public User loginUser(String username, String password) {
        return null;
    }

    public boolean addProduct(User seller, String type, double price, int year, String color, String condition) {
        return false;
    }

    public List<Product> searchProducts(String type, double minPrice, double maxPrice, String condition) {
        return null;
    }

    public boolean buyProduct(User buyer, int product) {
        return false;
    }

    public boolean makeOffer(User seller, int offer) {
        return false;
    }

    public List<Product> getPurchases(User buyer, String startDate, String endDate) {
        return null;
    }

    public boolean registerInterest(User user, String type) {
        return false;
    }

    public List<Product> getNotifications(User user) {
        return null;
    }

}
