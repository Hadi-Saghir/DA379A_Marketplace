package view;


import controller.Controller;
import view.subview.BuyMenu;
import view.subview.LoginMenu;
import view.subview.MainMenu;
import view.subview.SellMenu;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The implementation of the View interface. This class is the entry point for the main controller.
 */
public class MainView implements View {
    private Controller controller;
    private LoginMenu loginMenu;
    private MainMenu mainMenu;
    private BuyMenu buyMenu;
    private SellMenu sellMenu;

    /**
     * Sets the controller for the view and creates all the subviews.
     * @param controller the main controller of the application.
     */
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        loginMenu = new LoginMenu(this, controller);
        mainMenu = new MainMenu(this, controller);
        buyMenu = new BuyMenu(this, controller);
        sellMenu = new SellMenu(this, controller);
    }

    /**
     * Starts the applications view.
     */
    @Override
    public void launch() {
        System.out.println("|---------------------------------------------|");
        System.out.println("|-Welcome to the Warehouse Online Marketplace-|");
        System.out.println("|---------------------------------------------|");
        loginMenu.showLoginMenu();
    }

    /**
     * Shows a message to the user.
     * @param message content of the message to show.
     */
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Shows an error to the user.
     * @param error content of the error to show.
     */
    @Override
    public void showError(String error) {
        System.err.println(error);
        System.out.println();
    }

    /**
     * Shows the login menu to the user.
     */
    @Override
    public void showLoginMenu() {
        loginMenu.showLoginMenu();
    }

    /**
     * Indicates to the user that the login was successful and shows the main menu.
     */
    @Override
    public void loginSuccess() {
        loginMenu.loginSuccess();
    }

    /**
     * Indicates to the user that the login was unsuccessful and shows the login menu.
     */
    @Override
    public void loginFailure() {
        loginMenu.loginFailure();
    }

    /**
     * Indicates to the user whether the user account was created successfully and shows the login menu.
     * @param success whether the user was created successfully.
     */
    @Override
    public void userCreation(boolean success) {
        if(success) {
            showMessage("User created successfully");
        } else {
            showError(controller.getLatestError());
        }
        showLoginMenu();
    }

    /**
     * Prompts for input from the user.
     * @param prompt the message to show before the user input.
     * @return the user input.
     */
    public String promptForInput(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        return in.nextLine();
    }

    /**
     * Shows the main menu to the user.
     */
    public void showMainMenu() {
        mainMenu.showMainMenu();
    }

    /**
     * Shows the buy menu to the user.
     */
    public void showBuyMenu() {
        buyMenu.showBuyMenu(controller.getProductType(), controller.getConditions());
    }

    /**
     * Shows a notification to the user.
     * @param message The content of the notification.
     */
    @Override
    public void showNotification(String message) {
        showMessage(message);
    }

    /**
     * Shows the sell menu to the user.
     */
    public void showSellMenu() {
        sellMenu.showSellMenu();
    }

    /**
     * Shows the users purchase history to the user.
     */
    public void showBuyHist() {
        LocalDate start, end;

        do {
            String s = promptForInput("Enter start date (yyyy-mm-dd): ");
            try {
                start = LocalDate.parse(s);
            } catch(Exception e) {
                showError("Invalid date");
                start = null;
            }
        } while(start == null);

        do {
            String s = promptForInput("Enter end date (yyyy-mm-dd): ");
            try {
                end = LocalDate.parse(s);
            } catch(Exception e) {
                showError("Invalid date");
                end = null;
            }
        } while(end == null);

        List<String> history = controller.getBuyHist(start, end);
    }
}
