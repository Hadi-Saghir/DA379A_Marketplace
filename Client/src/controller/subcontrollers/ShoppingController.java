package controller.subcontrollers;

import controller.MainController;
import model.Cart;
import shared.Product;
import shared.Request;

import java.util.List;

public class ShoppingController {
    private final MainController mainController;
    private Cart cart;

    public ShoppingController(MainController mainController) {
        this.mainController = mainController;
        this.cart = new Cart(mainController);
    }

    public void provideStoreItems(List<Product> products) {
        cart.updateCatalog(products);
        mainController.listPurchasableProduct(cart.getProductsForView());
    }

    public void addProductToCart(int productId) {
        Product product = cart.addProductToCart(productId);
        mainController.productAddedToCart(product != null);
    }

    public void viewCart() {
        mainController.listCartContent(cart.getCartForView());
    }

    public void removeProductFromCart(int cartOrderId) {
        cart.removeProductFromCart(cartOrderId);
    }

    public void checkout() {
        for(Product product: cart.getProductsFromCart()) {
            mainController.makeOffer(product);
        }
    }
}
