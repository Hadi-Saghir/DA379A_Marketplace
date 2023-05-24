package controller.subcontrollers;

import controller.MainController;
import model.Cart;
import model.Lock;
import shared.Product;
import shared.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingController {
    private final MainController mainController;
    private final Cart cart;
    private final Cart sellingCart;


    public ShoppingController(MainController mainController) {
        this.mainController = mainController;
        this.cart = new Cart(mainController);
        this.sellingCart = new Cart(mainController);
    }

    public void handleProducts(Response response) {
        List<Product> products = response.MESSAGE().stream().map(o -> (Product) o).collect(Collectors.toList());
        cart.updateCatalog(products);
        cart.unlock();
    }

    public void handleMyProducts(Response response) {
        List<Product> products = response.MESSAGE().stream().map(o -> (Product) o).collect(Collectors.toList());
        sellingCart.updateCatalog(products);
        sellingCart.unlock();
    }

    public void lockCart() {
        cart.lock();
    }

    public void lockSellingCart() {
        sellingCart.lock();
    }

    public void waitForCartToUpdate() {
        cart.waitUntilUnlocked();
    }

    public void waitForSellingCartToUpdate() {
        sellingCart.waitUntilUnlocked();
    }

    public List<String> getCartForView() {
        return cart.getCartForView();
    }

    public void updateCatalog(List<Product> products) {
        cart.updateCatalog(products);
    }

    public HashMap<Integer, String> getProductsForView() {
        return cart.getProductsForView();
    }

    public boolean addProductToCart(int productId) {
        Product product = cart.addProductToCart(productId);
        return product != null;
    }


    public boolean removeProductFromCart(int cartOrderId) {
        return cart.removeProductFromCart(cartOrderId);
    }

    public void checkout() {
        for(Product product: cart.getProductsFromCart()) {
            mainController.makeOffer(product);
        }
    }

    public HashMap<Integer, String> getMyProductsForView() {
        return sellingCart.getProductsForView();
    }

    public HashMap<String, String> getMyProductDetails(int index) {
        Product product = sellingCart.getProduct(index);
        HashMap<String, String> details = new HashMap<>();
        if(product != null) {
            details.put("Type", product.getType().toString());
            details.put("Price", String.valueOf(product.getPrice()));
            details.put("Year of production", String.valueOf(product.getYearOfProduction()));
            details.put("Color", product.getColor());
            details.put("Condition", product.getCondition().toString());
            mainController.requestMyProductDetails(product);
        }
        return details;
    }
}
