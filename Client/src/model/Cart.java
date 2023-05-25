package model;

import controller.MainController;
import shared.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A model class for contaning items that can be bought (products) and items that have been bought (cart).
 */
public class Cart {
    private MainController mainController;
    private List<Product> products;
    private final HashMap<Integer, Product> cart = new HashMap<>();
    private final Lock lock = new Lock();

    /**
     * Constructor for Cart.
     * @param mainController MainController of the application.
     */
    public Cart(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Updates the catalog of products.
     * @param products List of products to update the catalog with.
     */
    public void updateCatalog(List<Product> products) {
        if(this.products != null) {
            this.products.clear();
        }
        this.products = products;
    }

    /**
     * Updates the cart with the given content.
     * All old content will be removed.
     * @param cart The new values of the cart to update with.
     */
    public void updateCart(HashMap<Integer, Product> cart) {
        this.cart.clear();
        this.cart.putAll(cart);
    }

    /**
     * Returns the cart as a HashMap.
     * @return The content of the cart.
     */
    public HashMap<Integer, Product> getCart() {
        return cart;
    }

    /**
     * Returns products in a way that is suitable for the view.
     * @return HashMap of products.
     */
    public HashMap<Integer, String> getProductsForView() {
        HashMap<Integer, String> p = new HashMap<>();

        for(Product product: products) {
            StringBuilder row = new StringBuilder();
            row.append(product.getUsername()).append(" ")
               .append(product.getType()).append(" ")
               .append(product.getPrice()).append(" ")
               .append(product.getYearOfProduction())
               .append(" ").append(product.getColor())
               .append(" ").append(product.getCondition());

            if(cart.containsKey(product.getProductid())) {
                row.append(" ").append("In cart");
            } else {
               row.append(" ").append(product.getState());
            }

            p.put(product.getProductid(), row.toString());
        }

        return p;
    }

    /**
     * Returns a product with the given id.
     * @param productId Id of the product to return.
     * @return The product if found, otherwise null.
     */
    public Product getProduct(int productId) {
        for(Product product: products) {
            if(product.getProductid() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Adds a product to the cart. If the product is already in the cart, it will not be added.
     * If the product is not available, it will not be added.
     * @param productId Id of the product to add.
     * @return The product if it was added, otherwise null.
     */
    public Product addProductToCart(int productId) {
        Product product = getProduct(productId);
        if(product.getState() == Product.ProductState.AVAILABLE && !cart.containsKey(product.getProductid())) {
            cart.put(product.getProductid(), product);
            return product;
        } else {
            return null;
        }
    }

    /**
     * Returns the cart in a way that is suitable for the view.
     * @return List of products.
     */
    public List<String> getCartForView() {
        List<String> p = new ArrayList<>();

        for(Product product: cart.values()) {
            String row = product.getUsername() + " " +
                    product.getType() + " " +
                    product.getPrice() + " " +
                    product.getYearOfProduction() + " " +
                    product.getColor() + " " +
                    product.getCondition() + " " +
                    "In cart";

            p.add(row);
        }

        return p;
    }

    /**
     * Removes a product from the cart.
     * @param cartOrderId Id of the product to remove.
     * @return True if the product was removed, otherwise false.
     */
    public boolean removeProductFromCart(int cartOrderId) {
        return cart.remove(cartOrderId) != null;
    }

    /**
     * Returns a list of products in the cart as Products instead of Strings.
     * @return List of products.
     */
    public List<Product> getProductsFromCart() {
        return cart.values().stream().toList();
    }

    /**
     * Unlocks the cart.
     */
    public void unlock() {
        lock.unlock();
    }

    /**
     * Locks the cart.
     */
    public void lock() {
        lock.lock();
    }

    /**
     * Waits until the cart is unlocked.
     */
    public void waitUntilUnlocked() {
        lock.waitUntilUnlocked();
    }

    /**
     * Remove all content of the cart.
     */
    public void clear() {
        cart.clear();
    }
}
