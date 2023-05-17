package view.subview;

import controller.InvalidFormatException;
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
        switch (input) {
            case "0" -> controller.exit();
            case "1" -> controller.login();
            case "2" -> controller.createAccount();
            default -> {
                mainView.showError("Invalid option");
                showLoginMenu();
            }
        }
    }

    public void getAccountDetails() {
        String firstName = null, lastName = null, dob = null, emailAddress = null, username = null, password = null;
        do {
            try {
                firstName = mainView.promptForInput("Enter first name: ");
                controller.setFirstName(firstName);
            } catch (InvalidFormatException e) {
                mainView.showError(e.getMessage());
            }
        } while(firstName == null);

        do {
            try {
                lastName = mainView.promptForInput("Enter last name: ");
                controller.setLastName(lastName);
            } catch (InvalidFormatException e) {
                mainView.showError(e.getMessage());
            }
        } while(lastName == null);

        do {
            try {
                dob = mainView.promptForInput("Enter date of birth (dd/mm/yyyy): ");
                controller.setDateOfBirth(dob);
            } catch (InvalidFormatException e) {
                mainView.showError(e.getMessage());
            }
        } while(dob == null);

        do {
            try {
                emailAddress = mainView.promptForInput("Enter email address: ");
                controller.setEmailAddress(emailAddress);
            } catch (InvalidFormatException e) {
                mainView.showError(e.getMessage());
            }
        } while(emailAddress == null);

        do {
            try {
                username = mainView.promptForInput("Enter username: ");
                controller.setUsername(username);
            } catch (InvalidFormatException e) {
                mainView.showError(e.getMessage());
            }
        } while(username == null);

        do {
            try {
                password = mainView.promptForInput("Enter password: ");
                controller.setPassword(password);
            } catch (InvalidFormatException e) {
                mainView.showError(e.getMessage());
            }
        } while(password == null);
    }
}
