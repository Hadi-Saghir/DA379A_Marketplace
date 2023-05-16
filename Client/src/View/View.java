package view;

import controller.Controller;

public interface View {
    void setController(Controller controller);
    void showMessage(String message);
    void showError(String error);
    String promptForInput(String prompt);
    void showWelcomeMessage();
    void showLoginMenu();
    void showMainMenu();
    void getAccountDetails();

    void showBuyMenu();
}
