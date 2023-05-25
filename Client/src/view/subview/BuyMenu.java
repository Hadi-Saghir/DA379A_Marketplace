package view.subview;

import controller.Controller;
import view.MainView;

import java.util.HashMap;
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
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|------------------Buy Menu--------------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|-0. Back                                     -|");
        mainView.showMessage("|-1. View products                            -|");
        mainView.showMessage("|-2. Search products                          -|");
        mainView.showMessage("|-3. View cart                                -|");
        mainView.showMessage("|-4. Checkout                                 -|");
        mainView.showMessage("|-5. Register interest                        -|");
        mainView.showMessage("|----------------------------------------------|");

        String input = mainView.promptForInput("Enter option: ");
        switch (input) {
            case "0" -> mainView.showMainMenu();
            case "1" -> listPurchasableProducts();
            case "2" -> search();
            case "3" -> viewCart();
            case "4" -> checkout();
            case "5" -> registerInterest();
            default -> {
                mainView.showError("Invalid option");
                showBuyMenu();
            }
        }
    }

    private void checkout() {
        mainView.showMessage("Checking out items in cart");
        controller.checkout();
        showBuyMenu();
    }

    private void viewCart() {
        List<String> products = controller.getCartForView();
        listCartContent(products);
    }

    private void listPurchasableProducts() {
        HashMap<Integer, String> products = controller.getProductList();
        listPurchasableProducts(products);
    }

    private void listPurchasableProducts(HashMap<Integer, String> products) {
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|------------------Products--------------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|-0. Back                                     -|");
        for(int i: products.keySet()) {
            mainView.showMessage("|-" + (i) + ". " + products.get(i));
        }

        String input;
        do {
            input = mainView.promptForInput("Enter option: ");
            if(input.equals("0")) {
                showBuyMenu();
            } else {
                try {
                    int index = Integer.parseInt(input);
                    if(!products.containsKey(index)) {
                        throw new NumberFormatException();
                    }

                    boolean added = controller.addProductToCart(index);
                    if(!added) throw new RuntimeException();
                } catch(NumberFormatException e) {
                    mainView.showError("Invalid option");
                    input = null;
                } catch(RuntimeException e) {
                    mainView.showError("Failed to add product to cart");
                    input = null;
                }
            }
        } while(input == null);
        showBuyMenu();
    }

    private void search() {
        String productType = getProductType();
        double minPrice = getMinPrice();
        double maxPrice = getMaxPrice();
        String searchCondition = getSearchCondition();

        HashMap<Integer, String> products = controller.searchProducts(productType, minPrice, maxPrice, searchCondition);
        listPurchasableProducts(products);
    }

    private String getProductType() {
        String input;
        do {
            for(int i = 0; i < productTypes.size(); i++) {
                mainView.showMessage(i + ". " + productTypes.get(i));
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

        return productTypes.get(Integer.parseInt(input));
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
            for(int i = 0; i < conditions.size(); i++) {
                mainView.showMessage(i + ". " + conditions.get(i));
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

        return conditions.get(Integer.parseInt(input));
    }



    public void productAddedToCart(boolean added) {
        if(added) {
            mainView.showMessage("Product added to cart");
        } else {
            mainView.showError("Product not added to cart");
        }
        showBuyMenu();
    }

    public void listCartContent(List<String> products) {
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|------------------Cart------------------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|-0. Back                                     -|");
        for(int i = 0; i < products.size(); i++) {
            mainView.showMessage("|-" + (i + 1) + ". " + products.get(i));
        }

        String input;
        do {
            input = mainView.promptForInput("Enter option: ");
            if(input.equals("0")) {
                showBuyMenu();
            } else {
                try {
                    int index = Integer.parseInt(input);
                    if(index < 1 || index > products.size()) {
                        throw new NumberFormatException();
                    }
                    boolean success = controller.removeProductFromCart(index - 1);
                    if(!success) throw new RuntimeException();
                } catch(NumberFormatException e) {
                    mainView.showError("Invalid option");
                    input = null;
                } catch(RuntimeException e) {
                    mainView.showError("Failed to remove product from cart");
                    input = null;
                }
            }

        } while(input == null);
    }

    private void registerInterest() {
        String productType = getProductType();
        controller.registerInterest(productType);
        showBuyMenu();
    }
}
