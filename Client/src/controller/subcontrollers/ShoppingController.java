package controller.subcontrollers;

import controller.MainController;
import model.Cart;
import shared.Product;
import shared.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingController {
    private final MainController mainController;
    private final Cart shoppingCart;
    private final Cart sellingCart;
    private final Cart historyCart;


    public ShoppingController(MainController mainController) {
        this.mainController = mainController;
        this.shoppingCart = new Cart(mainController);
        this.sellingCart = new Cart(mainController);
        this.historyCart = new Cart(mainController);
    }

    public void handleProducts(Response response) {
        List<Product> products = response.MESSAGE().stream().map(o -> (Product) o).collect(Collectors.toList());
        shoppingCart.updateCatalog(products);
        shoppingCart.unlock();
    }

    public void handleMyProducts(Response response) {
//        List<Product> products = response.MESSAGE().stream().map(o -> (Product) o).collect(Collectors.toList());
//        sellingCart.updateCatalog(products);
        sellingCart.unlock();
    }

    public void handleMyOffersProducts(Response response) {
        sellingCart.updateCart((HashMap<Integer, Product>) response.MESSAGE().get(0));
        sellingCart.unlock();
    }

    public void lockShoppingCart() {
        shoppingCart.lock();
    }

    public void lockSellingCart() {
        sellingCart.lock();
    }

    public void lockHistoryCart() {
        historyCart.lock();
    }

    public void waitForShoppingCartToUpdate() {
        shoppingCart.waitUntilUnlocked();
    }

    public void waitForSellingCartToUpdate() {
        sellingCart.waitUntilUnlocked();
    }

    public void waitForHistoryCartToUpdate() {
        historyCart.waitUntilUnlocked();
    }

    public List<String> getShoppingCartForView() {
        return shoppingCart.getCartForView();
    }

    public void updateCatalog(List<Product> products) {
        shoppingCart.updateCatalog(products);
    }

    public HashMap<Integer, String> getProductsForView() {
        return shoppingCart.getProductsForView();
    }

    public boolean addProductToCart(int productId) {
        Product product = shoppingCart.addProductToCart(productId);
        return product != null;
    }


    public boolean removeProductFromCart(int cartOrderId) {
        return shoppingCart.removeProductFromCart(cartOrderId);
    }

    public void checkout() {
        for(Product product: shoppingCart.getProductsFromCart()) {
            mainController.makeOffer(product);
        }
        shoppingCart.clear();
    }

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



    public void handlePurchaseHistory(Response response) {
        List<Product> products = new ArrayList<>();

        for(Object o : response.MESSAGE()) {
            products.add((Product) o);
        }

        historyCart.updateCatalog(products);
        historyCart.unlock();
    }

    public List<String> getHistoryCartForView() {
        return historyCart.getCartForView();
    }

    public void removeProductFromSellingCart(int id) {
        sellingCart.removeProductFromCart(id);
    }
}
