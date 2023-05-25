package controller.subcontrollers;

import controller.MainController;
import model.Cart;
import shared.Product;
import shared.Response;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingController {
    private final MainController mainController;
    private final Cart shoppingCart;
    private final Cart sellingCart;


    public ShoppingController(MainController mainController) {
        this.mainController = mainController;
        this.shoppingCart = new Cart(mainController);
        this.sellingCart = new Cart(mainController);
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

    public void lockCart() {
        shoppingCart.lock();
    }

    public void lockSellingCart() {
        sellingCart.lock();
    }

    public void waitForCartToUpdate() {
        shoppingCart.waitUntilUnlocked();
    }

    public void waitForSellingCartToUpdate() {
        sellingCart.waitUntilUnlocked();
    }

    public List<String> getCartForView() {
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
