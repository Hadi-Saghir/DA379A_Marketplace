package view;

import controller.Controller;

public interface View {
    void setController(Controller mainController);
    void launch();
    void showMessage(String message);
    void showError(String errorConnectingToServer);
    void showLoginMenu();
    void loginSuccess();

    void loginFailure();

    void userCreation(boolean success);

    void showMainMenu();

    void showNotification(String string);
}
