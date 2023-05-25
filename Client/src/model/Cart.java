package model;

import controller.MainController;
import shared.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart {
    private MainController mainController;
    private List<Product> products;
    private final HashMap<Integer, Product> cart = new HashMap<>();
    private final Lock lock = new Lock();

    public Cart(MainController mainController) {
        this.mainController = mainController;
    }

    public void updateCatalog(List<Product> products) {
        if(this.products != null) {
            this.products.clear();
        }
        this.products = products;
    }

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

    public Product getProduct(int productId) {
        for(Product product: products) {
            if(product.getProductid() == productId) {
                return product;
            }
        }
        return null;
    }

    public Product addProductToCart(int productId) {
        Product product = getProduct(productId);
        if(product.getState() == Product.ProductState.AVAILABLE && !cart.containsKey(product.getProductid())) {
            cart.put(product.getProductid(), product);
            return product;
        } else {
            return null;
        }
    }

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

    public boolean removeProductFromCart(int cartOrderId) {
        return cart.remove(cartOrderId) != null;
    }

    public List<Product> getProductsFromCart() {
        return cart.values().stream().toList();
    }

    public void unlock() {
        lock.unlock();
    }

    public void lock() {
        lock.lock();
    }

    public void waitUntilUnlocked() {
        lock.waitUntilUnlocked();
    }
}
