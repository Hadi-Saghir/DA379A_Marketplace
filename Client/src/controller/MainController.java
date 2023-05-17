package controller;

import controller.subcontrollers.ConnectionController;
import controller.subcontrollers.LoginController;
import controller.subcontrollers.ShoppingController;
import shared.Product;
import shared.Request;
import shared.Response;
import shared.User;
import view.View;

import java.io.IOException;
import java.util.ArrayList;
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

    public MainController(){
        connectionController = new ConnectionController(this, HOST, PORT);
        loginController = new LoginController(this);
        shoppingController = new ShoppingController(this);
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
            view.showMessage(e.getMessage());
            exit(1);
        }

        view.showWelcomeMessage();
        view.showLoginMenu();
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
    public void createAccount() {
        loginController.createAccount();
    }

    @Override
    public void setFirstName(String firstName) {
        loginController.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        loginController.setLastName(lastName);
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        loginController.setEmailAddress(emailAddress);
    }

    @Override
    public void setUsername(String username) {
        loginController.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        loginController.setPassword(password);
    }

    @Override
    public void setDateOfBirth(String dob) {
        loginController.setDateOfBirth(dob);
    }

    @Override
    public void sellProducts() {

    }

    @Override
    public void viewProductsBuying() {

    }

    @Override
    public void viewProductsSelling() {

    }

    @Override
    public void getProductList() {
        connectionController.doAllProducts();
    }

    @Override
    public void viewCart() {
        shoppingController.viewCart();
    }

    @Override
    public void checkout() {
        shoppingController.checkout();
    }

    @Override
    public void searchProducts(String productType, double minPrice, double maxPrice, String searchCondition) {
        connectionController.doProductSearch(productType, minPrice, maxPrice, searchCondition);
    }

    @Override
    public void addProductToCart(int productId) {
        shoppingController.addProductToCart(productId);
    }

    @Override
    public void removeProductFromCart(int cartOrderId) {
        shoppingController.removeProductFromCart(cartOrderId);
    }

    @Override
    public void logout() {
        connectionController.disconnectFromServer();
        view.showLoginMenu();
    }

    @Override
    public void buyProducts() {
        List<String> productTypes = Stream.of(Product.ProductType.values()).map(Product.ProductType::name).toList();
        List<String> conditions = Stream.of(Product.ProductCondition.values()).map(Product.ProductCondition::name).toList();
        view.showBuyMenu(productTypes, conditions);
    }

    @Override
    public void login() {
        loginController.login();
    }

    public void doCreateNewUser(User user) {
        connectionController.doCreateNewUser(user);
    }

    public void doLogin(String username, String password) {
        connectionController.doLogin(username, password);
    }

    public void handleResponse(Response response) {
        switch(response.RESPONSE_TYPE()) {
            case REGISTER -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    view.showMessage("User created successfully");
                } else {
                    view.showError("User creation failed");
                }

                view.showLoginMenu();
            }
            case LOGIN -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    view.loginSuccess();
                    view.showMainMenu();
                } else {
                    view.showError("Login failed");
                    view.showLoginMenu();
                }
            }
            case ALL_PRODUCTS, SEARCH_PRODUCT -> {
                if(response.RESPONSE_RESULT() == Response.ResponseResult.SUCCESS) {
                    List<Object> objects = response.MESSAGE();
                    List<Product> products = new ArrayList<>();

                    for(Object object : objects) {
                        products.add((Product) object);
                    }

                    shoppingController.provideStoreItems(products);
                } else {
                    view.showError("Error getting product list");
                    view.showMainMenu();
                }
            }

            case ADD_PRODUCT -> {

            }
            case SELL_PRODUCT -> {}
            case MAKE_OFFER -> {}
            case GET_PURCHASE_HISTORY -> {}
            case REGISTER_INTEREST -> {}
            case NOTIFICATION -> {
                view.showNotification(response.MESSAGE().get(0).toString());
            }
            default -> {
                view.showError("Unknown response type");
                view.showError(response.RESPONSE_TYPE().name());
                view.showMainMenu();
            }
        }
    }

    public void listPurchasableProduct(HashMap<Integer, String> products) {
        view.listPurchasableProduct(products);
    }

    public void listCartContent(List<String> products) {
        view.listCartContent(products);
    }

    public void productAddedToCart(boolean added) {
        view.productAddedToCart(added);
    }

    public String getUserId() {
        return loginController.getUserId();
    }

    public void makeOffer(Product product) {
        connectionController.makeOffer(product);
    }
}
