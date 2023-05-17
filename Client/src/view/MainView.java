package view;


import controller.Controller;
import view.subview.BuyMenu;
import view.subview.LoginMenu;
import view.subview.MainMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainView implements View {
    private Controller controller;
    private LoginMenu loginMenu;
    private MainMenu mainMenu;
    private BuyMenu buyMenu;

    public void setController(Controller controller) {
        this.controller = controller;
        loginMenu = new LoginMenu(this, controller);
        mainMenu = new MainMenu(this, controller);
        buyMenu = new BuyMenu(this, controller);
    }
    public void showMessage(String message) {System.out.println(message);}
    public void showError(String error) {
        System.err.println(error);
        System.out.println();
    }

    public String promptForInput(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        return in.nextLine();
    }

    public void showWelcomeMessage() {
        System.out.println("|---------------------------------------------|");
        System.out.println("|-Welcome to the Warehouse Online Marketplace-|");
        System.out.println("|---------------------------------------------|");
    }

    public void showLoginMenu() {
        loginMenu.showLoginMenu();
    }


    public void showMainMenu() {
        mainMenu.showMainMenu();
    }

    @Override
    public void getFirstName() {
        loginMenu.getFirstName();
    }

    @Override
    public void getLastName() {
        loginMenu.getLastName();
    }

    @Override
    public void getDateOfBirth() {
        loginMenu.getDateOfBirth();
    }

    @Override
    public void getEmailAddress() {
        loginMenu.getEmailAddress();
    }

    @Override
    public void getUsername() {
        loginMenu.getUsername();
    }

    @Override
    public void getPassword() {
        loginMenu.getPassword();
    }

    @Override
    public void showBuyMenu(List<String> productTypes, List<String> conditions) {
        buyMenu.showBuyMenu(productTypes, conditions);
    }

    @Override
    public void connectionError(String message) {
        showError("Error sending request to server. Please try again.");
        showError(message);
    }

    @Override
    public void parseError(String message) {
        showError("Error reading response from server. Please try again.");
        showError(message);
    }

    @Override
    public void listPurchasableProduct(HashMap<Integer, String> products) {
        buyMenu.listPurchasableProducts(products);
    }

    @Override
    public void loginSuccess() {
        loginMenu.loginSuccess();
    }

    @Override
    public void productAddedToCart(boolean added) {
        buyMenu.productAddedToCart(added);
    }

    @Override
    public void listCartContent(List<String> products) {
        buyMenu.listCartContent(products);
    }

    @Override
    public void showNotification(String message) {
        showMessage(message);
    }
}
