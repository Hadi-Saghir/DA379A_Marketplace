package controller;

import view.InputHandler;
import view.Menu;

import java.time.LocalDate;

public class MainController {
    private final ConnectionController connectionController;
    private final Menu menu;
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public MainController(){
        connectionController = new ConnectionController(this, HOST, PORT);
        menu = new Menu(this);
    }

    public void launch() {
        menu.showLoginMenu();
        String regex = "[0-2]";
        String input = InputHandler.getInput("Enter option: ", "Please enter a valid option", regex);
        switch(input) {
            case "0" -> exit();
            case "1" -> login();
            case "2" -> createNewUser();
        }
    }

    public void exit() {
        connectionController.disconnectFromServer();
        System.out.println("Good bye!");
        System.exit(0);
    }

    public void createNewUser() {
        String firstName = InputHandler.getInput("Enter first name: ", "Please enter a valid first name");
        String lastName = InputHandler.getInput("Enter last name: ", "Please enter a valid last name");
        LocalDate dob = InputHandler.getDate("Enter date of birth (YYYY-MM-DD): ", "Please enter a valid date");
        String emailAddress = InputHandler.getInput("Enter email address: ", "Please enter a valid email address");
        String username = InputHandler.getInput("Enter username: ", "Please enter a valid username");
        String password = InputHandler.getInput("Enter password: ", "Please enter a valid password");

        connectionController.doCreateNewUser(firstName, lastName, dob, emailAddress, username, password);
    }

    public void login() {
        String username = InputHandler.getInput("Enter username: ", "Please enter a valid username");
        String password = InputHandler.getInput("Enter password: ", "Please enter a valid password");
        connectionController.doLogin(username, password);
    }

    public void searchProduct() {

    }

    public void showMessage(String message) {
        menu.showMessage(message);
    }

    public void showError(String error) {
        menu.showError(error);
    }

    public void onLoginSuccess() {
        menu.showMainMenu();
    }
}
