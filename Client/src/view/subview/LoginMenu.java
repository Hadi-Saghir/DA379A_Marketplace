package view.subview;

import controller.Controller;
import view.MainView;

import java.util.concurrent.Callable;

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
            case "2" -> createAccount();
            default -> {
                mainView.showError("Invalid option");
                showLoginMenu();
            }
        }
    }

    private void login() {
        String username;
        do {
            username = mainView.promptForInput("Enter username: ");

            if(username.isBlank()) {
                mainView.showError("Username cannot be blank");
                username = null;
            } else if(username.contains(" ")) {
                mainView.showError("Username cannot contain spaces");
                username = null;
            }
        } while(username == null);

        String password;
        do {
            password = mainView.promptForInput("Enter password: ");

            if(password.isBlank()) {
                mainView.showError("Password cannot be blank");
                password = null;
            }
        } while(password == null);


        controller.login(username, password);
    }

    public void loginSuccess() {
        mainView.showMessage("|---------------Login successful---------------|");
        mainView.showMainMenu();
    }

    public void loginFailure() {
        mainView.showError("|----------------Login failure-----------------|");
        mainView.showLoginMenu();
    }

    private void createAccount() {
        Callable<Boolean> method;

        method = () -> controller.setFirstName(mainView.promptForInput("Enter first name: "));
        addUserInfo(method);

        method = () -> controller.setLastName(mainView.promptForInput("Enter last name: "));
        addUserInfo(method);

        method = () -> controller.setDateOfBirth(mainView.promptForInput("Enter date of birth (yyyy-mm-dd): "));
        addUserInfo(method);

        method = () -> controller.setEmailAddress(mainView.promptForInput("Enter email address: "));
        addUserInfo(method);

        method = () -> controller.setUsername(mainView.promptForInput("Enter username: "));
        addUserInfo(method);

        method = () -> controller.setPassword(mainView.promptForInput("Enter password: "));
        addUserInfo(method);

        controller.createAccount();
    }

    private void addUserInfo(Callable<Boolean> method) {
        boolean success;
        do {
            try {
                success = method.call();
            } catch (Exception e) {
                mainView.showError(e.getMessage());
                success = false;
            }

            if(!success) {
                mainView.showError(controller.getLatestError());
            }
        } while(!success);

    }
}
