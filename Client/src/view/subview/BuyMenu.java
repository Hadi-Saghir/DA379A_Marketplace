package view.subview;

import controller.Controller;
import view.MainView;

import java.util.HashMap;
import java.util.List;

/**
 * This class handles the buy menu and all input and output related to it.
 */
public class BuyMenu {
    private final MainView mainView;
    private final Controller controller;
    private List<String> productTypes;
    private List<String> conditions;

    /**
     * Constructs a buy menu with the specified main view and controller.
     * @param mainView The main view.
     * @param controller The controller.
     */
    public BuyMenu(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    /**
     * Shows the buy menu and sets the product types and conditions allowed.
     * @param productTypes Allowed product types.
     * @param conditions Allowed conditions.
     */
    public void showBuyMenu(List<String> productTypes, List<String> conditions) {
        this.productTypes = productTypes;
        this.conditions = conditions;
        showBuyMenu();
    }

    /**
     * Shows the buy menu asks the user for input.
     */
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

    /**
     * Calls the controller to check out all items in the cart.
     */
    private void checkout() {
        mainView.showMessage("Checking out items in cart");
        controller.checkout();
        showBuyMenu();
    }

    /**
     * Shows all the items in the cart.
     */
    private void viewCart() {
        List<String> products = controller.getCartForView();
        listCartContent(products);
    }

    /**
     * Calls the controller to get an updated list of purchasable products,
     * then shows the list.
     */
    private void listPurchasableProducts() {
        HashMap<Integer, String> products = controller.getProductList();
        listPurchasableProducts(products);
    }

    /**
     * Lists all purchasable products and asks the user which one to add to the cart.
     * @param products The purchasable products.
     */
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

    /**
     * Reads input to do a search, calls the controller to do the search and shows the results.
     * Then asks the user which product to add to the cart.
     */
    private void search() {
        String productType = getProductType();
        double minPrice = getMinPrice();
        double maxPrice = getMaxPrice();
        String searchCondition = getSearchCondition();

        HashMap<Integer, String> products = controller.searchProducts(productType, minPrice, maxPrice, searchCondition);
        listPurchasableProducts(products);
    }

    /**
     * Asks the user which product type they want.
     * @return The product type chosen by the user.
     */
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

    /**
     * Asks the user for a minimum price.
     * @return The minimum price chosen by the user.
     */
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

    /**
     * Asks the user for a maximum price.
     * @return The maximum price chosen by the user.
     */
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

    /**
     * Asks the user for a search condition.
     * @return The search condition chosen by the user.
     */
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

    /**
     * Lists all the items in the cart and asks the user which one to remove.
     */
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

    /**
     * Asks the user for a product type and calls the controller to register interest in that product type.
     */
    private void registerInterest() {
        String productType = getProductType();
        controller.registerInterest(productType);
        showBuyMenu();
    }
}
