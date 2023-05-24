package controller;

import view.View;

import java.util.HashMap;
import java.util.List;

public interface Controller {
    String getLatestError();
    void setView(View menu);

    void launch();
    void exit();

    void login(String username, String password);

    boolean setFirstName(String firstName);

    boolean setLastName(String lastName);

    boolean setDateOfBirth(String dateOfBirth);

    boolean setEmailAddress(String emailAddress);

    boolean setUsername(String username);

    boolean setPassword(String password);

    void createAccount();

    void logout();

    List<String> getProductType();

    List<String> getConditions();

    HashMap<Integer, String> getProductList();

    boolean addProductToCart(int productId);

    HashMap<Integer, String> searchProducts(String productType, double minPrice, double maxPrice, String searchCondition);

    boolean removeProductFromCart(int productId);

    List<String> getCartForView();

    void checkout();

    List<String> getAllowedTypes();

    List<String> getAllowedConditions();


    void addProduct(String type, Double price, Integer yearOfProduction, String colour, String condition);

    HashMap<Integer, String> getMyProducts();

    HashMap<String, String> getMyProductDetails(int index);
}
