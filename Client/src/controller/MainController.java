package controller;

import controller.subcontrollers.ConnectionController;
import controller.subcontrollers.LoginController;
import shared.Product;
import shared.User;
import view.View;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class MainController implements Controller {
    private final ConnectionController connectionController;
    private final LoginController loginController;
    private View view;
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public MainController(){
        connectionController = new ConnectionController(this, HOST, PORT);
        loginController = new LoginController(this);
    }

    @Override
    public void setView(View view) {
        this.view = view;
        connectionController.setView(view);
        loginController.setView(view);
    }

    @Override
    public void launch() {
        view.showWelcomeMessage();
        view.showLoginMenu();
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
    public void setFirstName(String firstName) throws InvalidFormatException {
        loginController.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) throws InvalidFormatException {
        loginController.setLastName(lastName);
    }

    @Override
    public void setEmailAddress(String emailAddress) throws InvalidFormatException {
        loginController.setEmailAddress(emailAddress);
    }

    @Override
    public void setUsername(String username) throws InvalidFormatException {
        loginController.setUsername(username);
    }

    @Override
    public void setPassword(String password) throws InvalidFormatException {
        loginController.setPassword(password);
    }

    @Override
    public void setDateOfBirth(String dob) throws InvalidFormatException {
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

    }

    @Override
    public void viewCart() {

    }

    @Override
    public void checkout() {

    }

    @Override
    public void searchProducts(String productType, double minPrice, double maxPrice, String searchCondition) {
        connectionController.doProductSearch(productType, minPrice, maxPrice, searchCondition);
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

    public boolean doCreateNewUser(User user) throws IOException, ClassNotFoundException {
        return connectionController.doCreateNewUser(user);
    }

    public boolean doLogin(String username, String password) throws IOException, ClassNotFoundException {
        return connectionController.doLogin(username, password);
    }
}
