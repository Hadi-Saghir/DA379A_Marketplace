package view;

import controller.Controller;

/**
 * The main view of the application.
 */
public interface View {
    /**
     * Sets the controller for the view.
     * @param mainController the main controller of the application.
     */
    void setController(Controller mainController);

    /**
     * Launches the view.
     */
    void launch();

    /**
     * Shows a message to the user.
     * @param message
     */
    void showMessage(String message);

    /**
     * Shows an error to the user.
     * @param errorConnectingToServer
     */
    void showError(String errorConnectingToServer);

    /**
     * Shows the login menu.
     */
    void showLoginMenu();

    /**
     * Indicates that the login was successful and shows the main menu.
     */
    void loginSuccess();

    /**
     * Indicates that the login was unsuccessful and show the login menu again.
     */
    void loginFailure();

    /**
     * Prompts the user for account details to create a new user.
     * The controller is called to create the user.
     * @param success whether the user was created successfully.
     */
    void userCreation(boolean success);

    /**
     * Shows the main menu.
     */
    void showMainMenu();

    /**
     * Shows a notification to the user.
     * @param string The content of the notification.
     */
    void showNotification(String string);
}
