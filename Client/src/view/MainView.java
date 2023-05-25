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

public class MainView implements View {
    private Controller controller;
    private LoginMenu loginMenu;
    private MainMenu mainMenu;
    private BuyMenu buyMenu;
    private SellMenu sellMenu;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        loginMenu = new LoginMenu(this, controller);
        mainMenu = new MainMenu(this, controller);
        buyMenu = new BuyMenu(this, controller);
        sellMenu = new SellMenu(this, controller);
    }

    @Override
    public void launch() {
        System.out.println("|---------------------------------------------|");
        System.out.println("|-Welcome to the Warehouse Online Marketplace-|");
        System.out.println("|---------------------------------------------|");
        loginMenu.showLoginMenu();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showError(String error) {
        System.err.println(error);
        System.out.println();
    }

    @Override
    public void showLoginMenu() {
        loginMenu.showLoginMenu();
    }

    @Override
    public void loginSuccess() {
        loginMenu.loginSuccess();
    }

    @Override
    public void loginFailure() {
        loginMenu.loginFailure();
    }

    @Override
    public void userCreation(boolean success) {
        if(success) {
            showMessage("User created successfully");
        } else {
            showError(controller.getLatestError());
        }
        showLoginMenu();
    }


    public String promptForInput(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        return in.nextLine();
    }

    public void showMainMenu() {
        mainMenu.showMainMenu();
    }

    public void showBuyMenu() {
        buyMenu.showBuyMenu(controller.getProductType(), controller.getConditions());
    }

    @Override
    public void showNotification(String message) {
        showMessage(message);
    }

    public void showSellMenu() {
        sellMenu.showSellMenu();
    }

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
