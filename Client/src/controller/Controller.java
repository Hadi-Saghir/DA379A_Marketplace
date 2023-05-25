package controller;

import view.View;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * Main controller of the client, the entry point for the view.
 */
public interface Controller {
    /**
     * Returns the latest error message.
     * @return the latest error message.
     */
    String getLatestError();

    /**
     * Sets the view to be used by the controller.
     * @param menu the view to be used by the controller.
     */
    void setView(View menu);

    /**
     * Launches the application.
     */
    void launch();

    /**
     * Exits the application.
     */
    void exit();

    /**
     * Logs in the user.
     * @param username the username of the user.
     * @param password the password of the user.
     */
    void login(String username, String password);

    /**
     * Sets the first name of the user.
     * @param firstName the first name of the user.
     * @return true if the first name is valid, false otherwise.
     */
    boolean setFirstName(String firstName);

    /**
     * Sets the last name of the user.
     * @param lastName the last name of the user.
     * @return true if the last name is valid, false otherwise.
     */
    boolean setLastName(String lastName);

    /**
     * Sets the date of birth of the user.
     * @param dateOfBirth the date of birth of the user.
     * @return true if the date of birth is valid, false otherwise.
     */
    boolean setDateOfBirth(String dateOfBirth);

    /**
     * Sets the email address of the user.
     * @param emailAddress the email address of the user.
     * @return true if the email address is valid, false otherwise.
     */
    boolean setEmailAddress(String emailAddress);

    /**
     * Sets the username of the user.
     * @param username the username of the user.
     * @return true if the username is valid, false otherwise.
     */
    boolean setUsername(String username);

    /**
     * Sets the password of the user.
     * @param password the password of the user.
     * @return true if the password is valid, false otherwise.
     */
    boolean setPassword(String password);

    /**
     * Creates a new account.
     */
    void createAccount();

    /**
     * Logs out the user.
     */
    void logout();

    /**
     * Returns a list of all the product types allowed
     * as strings.
     * @return a list of all the product types allowed.
     */
    List<String> getProductType();

    /**
     * Returns a list of all the product conditions allowed
     * as strings.
     * @return a list of all the product conditions allowed.
     */
    List<String> getConditions();

    /**
     * Returns a list of all the product colours allowed
     * as strings.
     * @return a list of all the product colours allowed.
     */
    HashMap<Integer, String> getProductList();

    /**
     * Adds a product to the cart with the given id.
     * @param productId the id of the product to be added.
     * @return true if the product was added successfully, false otherwise.
     */
    boolean addProductToCart(int productId);

    /**
     * Sends a request to the server to search for a products matching the given criteria.
     * @param productType the type of the product.
     * @param minPrice the minimum price of the product.
     * @param maxPrice the maximum price of the product.
     * @param searchCondition the condition of the product.
     * @return a list of products matching the given criteria.
     */
    HashMap<Integer, String> searchProducts(String productType, double minPrice, double maxPrice, String searchCondition);

    /**
     * Removes a product from the cart with the given id.
     * @param productId the id of the product to be removed.
     * @return true if the product was removed successfully, false otherwise.
     */
    boolean removeProductFromCart(int productId);

    /**
     * Returns a list of all the products in the cart.
     * @return a list of all the products in the cart.
     */
    List<String> getCartForView();

    /**
     * Make an offer on all the products in the cart.
     */
    void checkout();

    /**
     * Returns a list of all allowed product types.
     * @return a list of all allowed product types.
     */
    List<String> getAllowedTypes();

    /**
     * Returns a list of all allowed product conditions.
     * @return a list of all allowed product conditions.
     */
    List<String> getAllowedConditions();

    /**
     * Lists a new product for sale.
     * @param type the type of the product.
     * @param price the price of the product.
     * @param yearOfProduction the year of production of the product.
     * @param colour the colour of the product.
     * @param condition the condition of the product.
     */
    void addProduct(String type, Double price, Integer yearOfProduction, String colour, String condition);

    /**
     * Returns a hashmap of all the products with offers.
     * @return all products with offers.
     */
    HashMap<Integer, String> getProductsWithOffer();

    /**
     * Subscribe to a product type, receiving notifications when a new product of that type is listed.
     * @param productType the product type to subscribe to.
     */
    void registerInterest(String productType);

    /**
     * Returns a list of purchases made by the user between the given dates.
     * @param start the start date.
     * @param end the end date.
     * @return a list of purchases made by the user between the given dates.
     */
    List<String> getBuyHist(LocalDate start, LocalDate end);

    /**
     * Accepts an offer and sells a product with the specified id.
     * @param id the id of the product to be sold.
     */
    void acceptOffer(int id);
}
