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

public class ConnectionController {
    private final MainController mainController;
    private View view;
    private final ServerHandler serverHandler;
    private ResponseHandler responseHandler;
    private RequestHandler requestHandler;
    private final ShoppingController shoppingController;


    public ConnectionController(MainController mainController, ShoppingController shoppingController, String host, int port) {
        this.mainController = mainController;
        this.serverHandler = new ServerHandler(host, port, this);
        this.shoppingController = shoppingController;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void connectToServer() throws IOException {
        serverHandler.connectToServer();
        view.showMessage("Connected to server");

        requestHandler = new RequestHandler(serverHandler.getOut());
        responseHandler = new ResponseHandler(serverHandler.getIn());

        requestHandler.start();
        responseHandler.start();
    }

    public void disconnectFromServer() {
        try {
            serverHandler.disconnectFromServer();
            view.showMessage("Disconnected from server");
        } catch (Exception e) {
            view.showError("Error disconnecting from server");
            view.showMessage(e.getMessage());
        }
    }

    public void doLogin(String username, String password) {
        Request request = Request.login(username, password);
        sendRequest(request);
    }

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

    public void doProductSearch(String productType, double minPrice, double maxPrice, String searchCondition) {
        shoppingController.lockShoppingCart();
        Request request = Request.searchProduct(productType, minPrice, maxPrice, Product.ProductCondition.valueOf(searchCondition));
        sendRequest(request);
    }

    public void doAllProducts() {
        shoppingController.lockShoppingCart();
        Request request = Request.allProducts();
        sendRequest(request);
    }

    public void makeOffer(Product product) {
        Request request = Request.makeOffer(product.getProductid(), mainController.getUserId(), product.getPrice());
        sendRequest(request);
    }

    public void doAddProduct(String type, String username, Double price, Integer yearOfProduction, String colour, String condition) {
        Product.ProductType productType = Product.ProductType.valueOf(type);
        Product.ProductCondition productCondition = Product.ProductCondition.valueOf(condition);

        Request request = Request.addProduct(productType, username, price, yearOfProduction, colour, productCondition);
        sendRequest(request);
    }

    public void getProductsWithOffer() {
        shoppingController.lockSellingCart();
        Request request = Request.getCurrentOffers(mainController.getUserId());
        sendRequest(request);
    }

    public void requestMyProductDetails(Product product) {
        //TODO Skicka en fråga med productid som ger detaljer om produkten
    }

    private void sendRequest(Request request) {
        requestHandler.sendRequest(request);
        mainController.handleResponse(responseHandler.getResponse());
    }

    public void registerInterest(String productType, String username) {
        Request request = Request.registerInterest(productType, username);
        sendRequest(request);
    }

    public ResponseHandler getResponseHandler() {
        return responseHandler;
    }

    public void getBuyHist(String userId, LocalDate start, LocalDate end) {
        shoppingController.lockHistoryCart();
        Request request = Request.getPurchaseHistory(userId, start, end);
        request.setUsername(userId);
        sendRequest(request);
    }

    public void acceptOffer(int id, String userId) {
        Request request = Request.sellProduct(id, userId);
        sendRequest(request);
    }
}
