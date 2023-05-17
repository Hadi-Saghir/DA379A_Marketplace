package controller;

import view.View;

public interface Controller {
    void exit();
    void setView(View view);
    void launch();
    void login();
    void createAccount();

    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setEmailAddress(String emailAddress);
    void setUsername(String username);
    void setPassword(String password);
    void setDateOfBirth(String dob);
    void logout();

    void buyProducts();

    void sellProducts();

    void viewProductsBuying();

    void viewProductsSelling();

    void getProductList();

    void viewCart();

    void checkout();

    void searchProducts(String productType, double minPrice, double maxPrice, String searchCondition);
}
