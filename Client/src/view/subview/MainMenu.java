package view.subview;

import controller.Controller;
import view.MainView;

/**
 * A view to handle the main menu after the user has logged in.
 */
public class MainMenu {
    private final MainView mainView;
    private final Controller controller;

    public MainMenu(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    /**
     * Displays the main menu and prompts the user for input.
     */
    public void showMainMenu() {
        System.out.println("|----------------------------------------------|");
        System.out.println("|------------------Main Menu-------------------|");
        System.out.println("|----------------------------------------------|");
        System.out.println("|-0. Logout                                   -|");
        System.out.println("|-1. Buy products                             -|");
        System.out.println("|-2. Sell products                            -|");
        System.out.println("|-3. Show buy history                         -|");
        System.out.println("|----------------------------------------------|");
        String input = mainView.promptForInput("Enter option: ");

        switch (input) {
            case "0" -> controller.logout();
            case "1" -> mainView.showBuyMenu();
            case "2" -> mainView.showSellMenu();
            case "3" -> mainView.showBuyHist();
            default -> {
                mainView.showError("Invalid option");
                showMainMenu();
            }
        }
    }
}
