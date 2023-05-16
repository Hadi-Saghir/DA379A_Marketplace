package controller;

import view.View;

public interface Controller {
    void exit();
    void setView(View view);
    void launch();
    void login();
    void createAccount();

    void setFirstName(String firstName) throws InvalidFormatException;
    void setLastName(String lastName) throws InvalidFormatException;
    void setEmailAddress(String emailAddress) throws InvalidFormatException;
    void setUsername(String username) throws InvalidFormatException;
    void setPassword(String password) throws InvalidFormatException;
    void setDateOfBirth(String dob) throws InvalidFormatException;
    void logout();

    void buyProducts();

    void sellProducts();

    void viewProductsBuying();

    void viewProductsSelling();

    void getProductList();

    void viewCart();

    void checkout();
}
