package controller.subcontrollers;

import controller.MainController;
import model.RequestHandler;
import model.ResponseHandler;
import model.ServerHandler;
import shared.Product;
import shared.Request;
import shared.User;
import view.View;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller for the server connection.
 */
public class ConnectionController {
    private final MainController mainController;
    private View view;
    private final ServerHandler serverHandler;
    private ResponseHandler responseHandler;
    private RequestHandler requestHandler;
    private final ShoppingController shoppingController;


    /**
     * Creates a controller for the server connection.
     * @param mainController The main controller
     * @param shoppingController The shopping controller
     * @param host Host IP or name
     * @param port Port number
     */
    public ConnectionController(MainController mainController, ShoppingController shoppingController, String host, int port) {
        this.mainController = mainController;
        this.serverHandler = new ServerHandler(host, port, this);
        this.shoppingController = shoppingController;
    }

    /**
     * Sets the view for the controller.
     * @param view The view
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Connects to the server.
     * @throws IOException If the connection fails
     */
    public void connectToServer() throws IOException {
        serverHandler.connectToServer();
        view.showMessage("Connected to server");

        requestHandler = new RequestHandler(serverHandler.getOut());
        responseHandler = new ResponseHandler(serverHandler.getIn());

        requestHandler.start();
        responseHandler.start();
    }

    /**
     * Disconnects from the server.
     */
    public void disconnectFromServer() {
        try {
            serverHandler.disconnectFromServer();
            view.showMessage("Disconnected from server");
        } catch (Exception e) {
            view.showError("Error disconnecting from server");
            view.showMessage(e.getMessage());
        }
    }

    /**
     * Sends a login request to the server.
     * @param username Username of the user to log in.
     * @param password Password of the user to log in.
     */
    public void doLogin(String username, String password) {
        Request request = Request.login(username, password);
        sendRequest(request);
    }

    /**
     * Sends a request to crate a new user.
     * @param user The user to create.
     */
    public void doCreateNewUser(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        LocalDate dateOfBirth = user.getDateOfBirth();
        String email = user.getEmailAddress();
        String username = user.getUsername();
        String password = user.getPassword();
        Request request = Request.register(firstName, lastName, dateOfBirth, email, username, password);

        sendRequest(request);
    }

    /**
     * Sends a request to search for products.
     * @param productType The type of product to search for.
     * @param minPrice The minimum price of the product.
     * @param maxPrice The maximum price of the product.
     * @param searchCondition The condition of the product.
     */
    public void doProductSearch(String productType, double minPrice, double maxPrice, String searchCondition) {
        shoppingController.lockShoppingCart();
        Request request = Request.searchProduct(productType, minPrice, maxPrice, Product.ProductCondition.valueOf(searchCondition));
        sendRequest(request);
    }

    /**
     * Sends a request to get all products.
     */
    public void doAllProducts() {
        shoppingController.lockShoppingCart();
        Request request = Request.allProducts();
        sendRequest(request);
    }

    /**
     * Sends a request to make an offer on a product.
     * @param product The product to make an offer on.
     */
    public void makeOffer(Product product) {
        Request request = Request.makeOffer(product.getProductid(), mainController.getUserId(), product.getPrice());
        sendRequest(request);
    }

    /**
     * Sends a request to offer a new product for sale.
     * @param type The type of product to sell.
     * @param username The username of the seller.
     * @param price The price of the product.
     * @param yearOfProduction The year of production of the product.
     * @param colour The colour of the product.
     * @param condition The condition of the product.
     */
    public void doAddProduct(String type, String username, Double price, Integer yearOfProduction, String colour, String condition) {
        Product.ProductType productType = Product.ProductType.valueOf(type);
        Product.ProductCondition productCondition = Product.ProductCondition.valueOf(condition);

        Request request = Request.addProduct(productType, username, price, yearOfProduction, colour, productCondition);
        sendRequest(request);
    }

    /**
     * Sends a request to get all offer for the current user.
     */
    public void getProductsWithOffer() {
        shoppingController.lockSellingCart();
        Request request = Request.getCurrentOffers(mainController.getUserId());
        sendRequest(request);
    }

    /**
     * Send a request to the server and wait for a response.
     * The response is then handled by the main controller.
     * @param request The request to send.
     */
    private void sendRequest(Request request) {
        requestHandler.sendRequest(request);
        mainController.handleResponse(responseHandler.getResponse());
    }

    /**
     * Sends a request to subscribe to a product type and get notifications
     * when a product of that type is added.
     * @param productType The type of product to subscribe to.
     * @param username The username of the user subscribing.
     */
    public void registerInterest(String productType, String username) {
        Request request = Request.registerInterest(productType, username);
        sendRequest(request);
    }

    /**
     * Returns the response handler created by this controller.
     * @return The response handler.
     */
    public ResponseHandler getResponseHandler() {
        return responseHandler;
    }

    /**
     * Sends a request to get the purchase history of a user.
     * @param userId The user to get the purchase history of.
     * @param start The start date of the purchase history.
     * @param end The end date of the purchase history.
     */
    public void getBuyHist(String userId, LocalDate start, LocalDate end) {
        shoppingController.lockHistoryCart();
        Request request = Request.getPurchaseHistory(userId, start, end);
        request.setUsername(userId);
        sendRequest(request);
    }

    /**
     * Sends a request to accept an offer on a product that is listed for sale.
     * @param id The id of the offer to accept.
     * @param userId The id of the user accepting the offer.
     */
    public void acceptOffer(int id, String userId) {
        Request request = Request.sellProduct(id, userId);
        sendRequest(request);
    }
}
