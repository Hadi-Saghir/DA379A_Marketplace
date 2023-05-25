package controller;

import controller.subcontrollers.ConnectionController;
import controller.subcontrollers.LoginController;
import controller.subcontrollers.ShoppingController;
import shared.Product;
import shared.Response;
import shared.User;
import view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class MainController implements Controller {
    private final ConnectionController connectionController;
    private final LoginController loginController;
    private final ShoppingController shoppingController;
    private View view;
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private String latestError = null;

    public MainController(){
        shoppingController = new ShoppingController(this);
        connectionController = new ConnectionController(this, shoppingController, HOST, PORT);
        loginController = new LoginController(this);
    }

    public String getLatestError() {
        return this.latestError;
    }

    public void setLatestError(String error) {
        this.latestError = error;
    }

    public void handleResponse(Response response) {
        switch(response.RESPONSE_TYPE()) {
            case REGISTER -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    view.userCreation(true);
                } else {
                    setLatestError("User creation failed");
                    view.userCreation(false);
                }
            }
            case LOGIN -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    view.loginSuccess();
                } else {
                    view.loginFailure();
                }
            }
            case ALL_PRODUCTS, SEARCH_PRODUCT -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    shoppingController.handleProducts(response);
                } else {
                    view.showError("Error getting product list");
                    view.showMainMenu();
                }
            }
            case ADD_PRODUCT -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    shoppingController.handleMyProducts(response);
                } else {
                    view.showError("Error getting my product list");
                    view.showMainMenu();
                }
            }
            case MAKE_OFFER -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    view.showNotification("Offer made successfully");
                } else {
                    view.showError("Error making offer");
                }
            }
            case REGISTER_INTEREST -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    view.showNotification("Interest registered successfully");
                } else {
                    view.showError("Error registering interest");
                }
            }
            case NOTIFICATION -> {
                view.showNotification(response.MESSAGE().get(0).toString());
            }

            case SELL_PRODUCT -> {}
            case GET_PURCHASE_HISTORY -> {}

            default -> {
                view.showError("Unknown response type");
                view.showError(response.RESPONSE_TYPE().name());
                view.showMainMenu();
            }
        }
    }

    @Override
    public void setView(View view) {
        this.view = view;
        connectionController.setView(view);
        loginController.setView(view);
    }

    @Override
    public void launch() {
        try {
            connectionController.connectToServer();
        } catch (IOException e) {
            view.showError("Error connecting to server");
            view.showError(e.getMessage());
            exit(1);
        }

        view.launch();
    }

    public void exit(int statusCode) {
        connectionController.disconnectFromServer();
        System.exit(statusCode);
    }

    @Override
    public void exit() {
        connectionController.disconnectFromServer();
        System.out.println("Good bye!");
        System.exit(0);
    }

    @Override
    public void login(String username, String password) {
        loginController.login(username, password);
    }

    public void doLogin(String username, String password) {
        connectionController.doLogin(username, password);
    }

    @Override
    public boolean setFirstName(String firstName) {
        return loginController.setFirstName(firstName);
    }

    @Override
    public boolean setLastName(String lastName) {
        return loginController.setLastName(lastName);
    }

    @Override
    public boolean setDateOfBirth(String dateOfBirth) {
        return loginController.setDateOfBirth(dateOfBirth);
    }

    @Override
    public boolean setEmailAddress(String emailAddress) {
        return loginController.setEmailAddress(emailAddress);
    }

    @Override
    public boolean setUsername(String username) {
        return loginController.setUsername(username);
    }

    @Override
    public boolean setPassword(String password) {
        return loginController.setPassword(password);
    }

    @Override
    public void createAccount() {
        User user = loginController.getUser();
        connectionController.doCreateNewUser(user);
    }

    @Override
    public void logout() {
        connectionController.disconnectFromServer();
        view.showLoginMenu();
    }

    @Override
    public List<String> getProductType() {
        return Stream.of(Product.ProductType.values()).map(Product.ProductType::name).toList();
    }

    @Override
    public List<String> getConditions() {
        return Stream.of(Product.ProductCondition.values()).map(Product.ProductCondition::name).toList();
    }

    @Override
    public HashMap<Integer, String> getProductList() {
        connectionController.doAllProducts();
        shoppingController.waitForCartToUpdate();
        return shoppingController.getProductsForView();
    }

    @Override
    public HashMap<Integer, String> searchProducts(String productType, double minPrice, double maxPrice, String searchCondition) {
        connectionController.doProductSearch(productType, minPrice, maxPrice, searchCondition);
        shoppingController.waitForCartToUpdate();
        return shoppingController.getProductsForView();
    }

    @Override
    public void checkout() {
        shoppingController.checkout();
    }

    @Override
    public List<String> getAllowedTypes() {
        return getProductType();
    }

    @Override
    public List<String> getAllowedConditions() {
        return getConditions();
    }

    @Override
    public void addProduct(String type, Double price, Integer yearOfProduction, String colour, String condition) {
        String username = loginController.getUser().getUsername();
        connectionController.doAddProduct(type, username, price, yearOfProduction, colour, condition);
    }

    @Override
    public HashMap<Integer, String> getProductsWithOffer() {
        connectionController.getProductsWithOffer();
        shoppingController.waitForSellingCartToUpdate();
        return shoppingController.getProductsWithOfferForView();
    }

    @Override
    public HashMap<String, String> getMyProductDetails(int index) {
        return shoppingController.getMyProductDetails(index);
    }

    @Override
    public void registerInterest(String productType) {
        String username = loginController.getUser().getUsername();
        connectionController.registerInterest(productType, username);
    }


    @Override
    public boolean addProductToCart(int productId) {
        return shoppingController.addProductToCart(productId);
    }

    @Override
    public boolean removeProductFromCart(int cartOrderId) {
        return shoppingController.removeProductFromCart(cartOrderId);
    }

    @Override
    public List<String> getCartForView() {
        return shoppingController.getCartForView();
    }

    public String getUserId() {
        return loginController.getUserId();
    }

    public void makeOffer(Product product) {
        connectionController.makeOffer(product);
    }

    public void requestMyProductDetails(Product product) {
        connectionController.requestMyProductDetails(product);
        shoppingController.waitForSellingCartToUpdate();
    }
}
