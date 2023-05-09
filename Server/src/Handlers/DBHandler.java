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

    public boolean addProduct(String seller, String type, double price, int year, String color, String condition) {
        return false;
    }

    public List<Product> searchProducts(String type, double minPrice, double maxPrice, String condition) {
        return null;
    }

    public boolean sellProduct(int seller, int offerId) {
        return false;
    }

    public boolean makeOffer(int buyer, int productId) {
        return false;
    }

    public List<Product> getPurchases(int buyer, String startDate, String endDate) {
        return null;
    }

    public boolean registerInterest(int user, String type) {
        return false;
    }

    public List<Product> getNotifications(int user) {
        return null;
    }

    public boolean addNotification(String user,String message){
        return false;
    }

}
