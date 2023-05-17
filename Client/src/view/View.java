package view;

import controller.Controller;

import java.util.List;

public interface View {
    void setController(Controller controller);
    void showMessage(String message);
    void showError(String error);
    String promptForInput(String prompt);
    void showWelcomeMessage();
    void showLoginMenu();
    void showMainMenu();
    void getFirstName();
    void getLastName();
    void getDateOfBirth();
    void getEmailAddress();
    void getUsername();
    void getPassword();

    void showBuyMenu(List<String> productTypes, List<String> conditions);

    void connectionError(String message);

    void parseError(String message);
    void listPurchasableProducts(List<String> products);
}
