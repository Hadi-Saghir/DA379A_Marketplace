package view.menu;

import controller.Controller;

public class BuyMenu {
    private final MainView MainView;
    private final Controller controller;

    public BuyMenu(MainView MainView, Controller controller) {
        this.MainView = MainView;
        this.controller = controller;
    }

    public void showBuyMenu() {
        System.out.println("|----------------------------------------------|");
        System.out.println("|------------------Buy Menu--------------------|");
        System.out.println("|----------------------------------------------|");
        System.out.println("|-0. Back                                     -|");
        System.out.println("|-1. View products                            -|");
        System.out.println("|-2. Search products                          -|");
        System.out.println("|-3. View cart                                -|");
        System.out.println("|-4. Checkout                                 -|");
        System.out.println("|----------------------------------------------|");

        String input = MainView.promptForInput("Enter option: ");
        switch (input) {
            case "0" -> MainView.showMainMenu();
            case "1" -> controller.getProductList();
            case "2" -> search();
            case "3" -> controller.addProductToCart();
            case "4" -> controller.viewCart();
            case "5" -> controller.checkout();
            default -> {
                MainView.showError("Invalid option");
                showBuyMenu();
            }
        }
    }

    private void search() {
        System.out.println("|----------------------------------------------|");
        System.out.println("|------------------Search Menu-----------------|");
        System.out.println("|----------------------------------------------|");
        System.out.println("|-0. Back                                     -|");
        System.out.println("|-1. Search by type                           -|");
        System.out.println("|-2. Search by price                          -|");
        System.out.println("|-3. Search by year of production             -|");
        System.out.println("|-4. Search by color                          -|");
        System.out.println("|-5. Search by condition                      -|");
        System.out.println("|----------------------------------------------|");

        String input = MainView.promptForInput("Enter option: ");
        switch (input) {
            case "0" -> showBuyMenu();
            case "1" -> searchByType(MainView.promptForInput("Enter type: "));
            case "2" -> searchByPrice(MainView.promptForInput("Enter price: "));
            case "3" -> searchByYearOfProduction(MainView.promptForInput("Enter year of production: "));
            case "4" -> searchByColor(MainView.promptForInput("Enter color: "));
            case "5" -> searchByCondition(MainView.promptForInput("Enter condition: "));
            default -> {
                MainView.showError("Invalid option");
                search();
            }
        }
    }

    private void searchByType(String type) {
        controller.searchByType(type);
    }

    private void searchByPrice(String price) {
        controller.searchByPrice(price);
    }

    private void searchByYearOfProduction(String yearOfProduction) {
        controller.searchByYearOfProduction(yearOfProduction);
    }

    private void searchByColor(String color) {
        controller.searchByColor(color);
    }

    private void searchByCondition(String condition) {
        controller.searchByCondition(condition);
    }
}
