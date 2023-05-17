package view.subview;

import controller.Controller;
import view.MainView;

import java.util.List;

public class BuyMenu {
    private final MainView mainView;
    private final Controller controller;
    private List<String> productTypes;
    private List<String> conditions;


    public BuyMenu(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    public void showBuyMenu(List<String> productTypes, List<String> conditions) {
        this.productTypes = productTypes;
        this.conditions = conditions;
        showBuyMenu();
    }

    private void showBuyMenu() {
        System.out.println("|----------------------------------------------|");
        System.out.println("|------------------Buy Menu--------------------|");
        System.out.println("|----------------------------------------------|");
        System.out.println("|-0. Back                                     -|");
        System.out.println("|-1. View products                            -|");
        System.out.println("|-2. Search products                          -|");
        System.out.println("|-3. View cart                                -|");
        System.out.println("|-4. Checkout                                 -|");
        System.out.println("|----------------------------------------------|");

        String input = mainView.promptForInput("Enter option: ");
        switch (input) {
            case "0" -> mainView.showMainMenu();
            case "1" -> controller.getProductList();
            case "2" -> search();
            // case "3" -> controller.addProductToCart(); //TODO
            case "4" -> controller.viewCart();
            case "5" -> controller.checkout();
            default -> {
                mainView.showError("Invalid option");
                showBuyMenu();
            }
        }
    }

    private void search() {
        String productType = getProductType();
        double minPrice = getMinPrice();
        double maxPrice = getMaxPrice();
        String searchCondition = getSearchCondition();

        controller.searchProducts(productType, minPrice, maxPrice, searchCondition);
    }

    private String getProductType() {
        String input;
        do {
            for(int i = 0; i < productTypes.size(); i++) {
                System.out.println(i + ". " + productTypes.get(i));
            }
            input = mainView.promptForInput("Enter product type: ");

            try {
                int index = Integer.parseInt(input);
                if(index < 0 || index >= productTypes.size()) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                mainView.showError("Invalid product type");
                input = null;
            }
        } while(input == null);

        return input;
    }

    private double getMinPrice() {
        String input;
        do {
            input = mainView.promptForInput("Enter minimum price: ");

            try {
                double price = Double.parseDouble(input);
                if(price < 0) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                mainView.showError("Invalid price");
                input = null;
            }
        } while(input == null);

        return Double.parseDouble(input);
    }

    private double getMaxPrice() {
        String input;
        do {
            input = mainView.promptForInput("Enter maximum price: ");

            try {
                double price = Double.parseDouble(input);
                if(price < 0) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                mainView.showError("Invalid price");
                input = null;
            }
        } while(input == null);

        return Double.parseDouble(input);
    }

    private String getSearchCondition() {
        String input;
        do {
            for(int i = 0; i < productTypes.size(); i++) {
                System.out.println(i + ". " + conditions.get(i));
            }
            input = mainView.promptForInput("Enter condition: ");

            try {
                int index = Integer.parseInt(input);
                if(index < 0 || index >= conditions.size()) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                mainView.showError("Invalid condition");
                input = null;
            }
        } while(input == null);

        return input;
    }

    public void listPurchasableProducts(List<String> products) {
        System.out.println("|----------------------------------------------|");
        System.out.println("|------------------Products--------------------|");
        System.out.println("|----------------------------------------------|");
        System.out.println("|-0. Back                                     -|");
        for(int i = 0; i < products.size(); i++) {
            System.out.println("|-" + (i + 1) + ". " + products.get(i));
        }
        System.out.println("|----------------------------------------------|");

        String input = mainView.promptForInput("Enter option: ");
        switch (input) {
            case "0" -> showBuyMenu();
            default -> {
                try {
                    int index = Integer.parseInt(input);
                    if(index < 1 || index > products.size()) {
                        throw new NumberFormatException();
                    }
                    // controller.addProductToCart(index - 1);
                } catch(NumberFormatException e) {
                    mainView.showError("Invalid option");
                    listPurchasableProducts(products);
                }
            }
        }
    }
}
