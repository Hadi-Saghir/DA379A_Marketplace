package view.subview;

import controller.Controller;
import view.MainView;

public class MainMenu {
    private final MainView mainView;
    private final Controller controller;

    public MainMenu(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    public void showMainMenu() {
        System.out.println("|----------------------------------------------|");
        System.out.println("|------------------Main Menu-------------------|");
        System.out.println("|----------------------------------------------|");
        System.out.println("|-0. Logout                                   -|");
        System.out.println("|-1. Buy products                             -|");
        System.out.println("|-2. Sell products                            -|");
        System.out.println("|-3. View products (selling)                  -|");
        System.out.println("|-3. View products (buying)                   -|");
        System.out.println("|----------------------------------------------|");
        String input = mainView.promptForInput("Enter option: ");

        switch (input) {
            case "0" -> controller.logout();
            case "1" -> controller.buyProducts();
            case "2" -> controller.sellProducts();
            case "3" -> controller.viewProductsBuying();
            case "4" -> controller.viewProductsSelling();
            default -> {
                mainView.showError("Invalid option");
                showMainMenu();
            }
        }
    }
}
