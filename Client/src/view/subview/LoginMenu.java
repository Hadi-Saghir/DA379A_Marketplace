package view.subview;

import controller.Controller;
import view.MainView;

public class LoginMenu {
    private final MainView mainView;
    private final Controller controller;

    public LoginMenu(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    public void showLoginMenu() {
        System.out.println();
        System.out.println("Please select an option:");
        System.out.println("0. Exit");
        System.out.println("1. Login");
        System.out.println("2. Create new user");
        System.out.print("Enter option: ");

        String input = mainView.promptForInput("");
        switch(input) {
            case "0" -> controller.exit();
            case "1" -> login();
            case "2" -> controller.createAccount();
            default -> {
                mainView.showError("Invalid option");
                showLoginMenu();
            }
        }
    }

    public void getFirstName() {
        controller.setFirstName(mainView.promptForInput("Enter first name: "));
    }

    public void getLastName() {
        controller.setLastName(mainView.promptForInput("Enter last name: "));
    }

    public void getDateOfBirth() {
        controller.setDateOfBirth(mainView.promptForInput("Enter date of birth (yyyy-mm-dd): "));
    }

    public void getEmailAddress() {
        controller.setEmailAddress(mainView.promptForInput("Enter email address: "));
    }

    public void getUsername() {
        controller.setUsername(mainView.promptForInput("Enter username: "));
    }

    public void getPassword() {
        controller.setPassword(mainView.promptForInput("Enter password: "));
    }

    private void login() {
        getUsername();
        getPassword();
        controller.login();
    }

    public void loginSuccess() {
        mainView.showMessage("");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("|---------------Login successful---------------|");
        mainView.showMessage("|----------------------------------------------|");
        mainView.showMessage("");
    }
}
