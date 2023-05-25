package controller.subcontrollers;

import controller.MainController;
import model.Cart;
import shared.Product;
import shared.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for the all products that can be bought, for all products that is being sold and for the history of
 * products that has been sold, as well as all the products that the user want to buy.
 */
public class ShoppingController {
    private final MainController mainController;
    private final Cart shoppingCart;
    private final Cart sellingCart;
    private final Cart historyCart;


    /**
     * Constructor for the ShoppingController class
     * @param mainController the main controller of the application.
     */
    public ShoppingController(MainController mainController) {
        this.mainController = mainController;
        this.shoppingCart = new Cart(mainController);
        this.sellingCart = new Cart(mainController);
        this.historyCart = new Cart(mainController);
    }

    /**
     * Handles the response from the server containing the products and updates the shopping cart.
     * Unlocks the shopping cart once the update is done.
     * @param response the response from the server.
     */
    public void handleProducts(Response response) {
        List<Product> products = response.MESSAGE().stream().map(o -> (Product) o).collect(Collectors.toList());
        shoppingCart.updateCatalog(products);
        shoppingCart.unlock();
    }

    /**
     * Unlocks the selling cart.
     * @param response the response from the server.
     */
    public void handleMyProducts(Response response) {
//        List<Product> products = response.MESSAGE().stream().map(o -> (Product) o).collect(Collectors.toList());
//        sellingCart.updateCatalog(products);
        sellingCart.unlock();
    }

    /**
     * Handles the response from the server containing the requested user's offers and updates the selling cart.
     * Unlocks the selling cart once the update is done.
     * @param response the response from the server.
     */
    public void handleMyOffersProducts(Response response) {
        sellingCart.updateCart((HashMap<Integer, Product>) response.MESSAGE().get(0));
        sellingCart.unlock();
    }

    /**
     * Locks the shopping cart.
     */
    public void lockShoppingCart() {
        shoppingCart.lock();
    }

    /**
     * Locks the selling cart.
     */
    public void lockSellingCart() {
        sellingCart.lock();
    }

    /**
     * Locks the history cart.
     */
    public void lockHistoryCart() {
        historyCart.lock();
    }

    /**
     * Waits until the shopping cart is unlocked.
     */
    public void waitForShoppingCartToUpdate() {
        shoppingCart.waitUntilUnlocked();
    }

    /**
     * Waits until the selling cart is unlocked.
     */
    public void waitForSellingCartToUpdate() {
        sellingCart.waitUntilUnlocked();
    }

    /**
     * Waits until the history cart is unlocked.
     */
    public void waitForHistoryCartToUpdate() {
        historyCart.waitUntilUnlocked();
    }

    /**
     * Returns the shopping cart in a format that can be displayed in the view.
     * @return The content of the shopping cart as a List.
     */
    public List<String> getShoppingCartForView() {
        return shoppingCart.getCartForView();
    }

    /**
     * Returns the selling cart in a format that can be displayed in the view.
     * @return The content of the selling cart as a HashMap.
     */
    public HashMap<Integer, String> getProductsForView() {
        return shoppingCart.getProductsForView();
    }

    /**
     * Adds a product to the shopping cart and returns true if the product was added successfully.
     * @param productId the id of the product to be added.
     * @return true if the product was added successfully, false otherwise.
     */
    public boolean addProductToCart(int productId) {
        Product product = shoppingCart.addProductToCart(productId);
        return product != null;
    }


    /**
     * Removes a product from the shopping cart and returns true if the product was removed successfully.
     * @param cartOrderId the id of the product to be removed.
     * @return true if the product was removed successfully, false otherwise.
     */
    public boolean removeProductFromCart(int cartOrderId) {
        return shoppingCart.removeProductFromCart(cartOrderId);
    }

    /**
     * Checks all products in the shopping cart.
     * This means making an offer and sending a request to server for each product.
     * The shopping cart is then cleared once done.
     */
    public void checkout() {
        for(Product product: shoppingCart.getProductsFromCart()) {
            mainController.makeOffer(product);
        }
        shoppingCart.clear();
    }

    /**
     * Returns a hashmap of the products with offers in the selling cart.
     * The product is formatted into a string that can be displayed in the view.
     * @return a hashmap of the products with offers in the selling cart.
     */
    public HashMap<Integer, String> getProductsWithOfferForView() {
        HashMap<Integer, Product> products = sellingCart.getCart();
        HashMap<Integer, String> productsWithOffer = new HashMap<>();
        for(int i: products.keySet()) {
            Product product = products.get(i);
            StringBuilder row = new StringBuilder();
            row.append(product.getUsername()).append(" ")
                    .append(product.getType()).append(" ")
                    .append(product.getPrice()).append(" ")
                    .append(product.getYearOfProduction())
                    .append(" ").append(product.getColor())
                    .append(" ").append(product.getCondition())
                    .append(" ").append(product.getState());
            productsWithOffer.put(i, row.toString());
        }

        return productsWithOffer;
    }

    /**
     * Handle the response from the server containing the purchase history of the user.
     * @param response the response from the server with the purchase history.
     */
    public void handlePurchaseHistory(Response response) {
        List<Product> products = new ArrayList<>();

        for(Object o : response.MESSAGE()) {
            products.add((Product) o);
        }

        historyCart.updateCatalog(products);
        historyCart.unlock();
    }

    /**
     * Returns the history cart in a format that can be displayed in the view.
     * @return The content of the history cart as a List.
     */
    public List<String> getHistoryCartForView() {
        return historyCart.getCartForView();
    }

    /**
     * Remove a product from the selling cart.
     * @param id the id of the product to be removed.
     */
    public void removeProductFromSellingCart(int id) {
        sellingCart.removeProductFromCart(id);
    }
}
