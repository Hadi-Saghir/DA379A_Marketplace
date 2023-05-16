package view.menu;

import controller.InvalidFormatException;
import controller.Controller;

public class LoginMenu {
    private final MainView MainView;
    private final Controller controller;

    public LoginMenu(MainView MainView, Controller controller) {
        this.MainView = MainView;
        this.controller = controller;
    }

    public void showLoginMenu() {
        System.out.println();
        System.out.println("Please select an option:");
        System.out.println("0. Exit");
        System.out.println("1. Login");
        System.out.println("2. Create new user");
        System.out.print("Enter option: ");

        String input = MainView.promptForInput("");
        switch (input) {
            case "0" -> controller.exit();
            case "1" -> controller.login();
            case "2" -> controller.createAccount();
            default -> {
                MainView.showError("Invalid option");
                showLoginMenu();
            }
        }
    }

    public void getAccountDetails() {
        String firstName = null, lastName = null, dob = null, emailAddress = null, username = null, password = null;
        do {
            try {
                firstName = MainView.promptForInput("Enter first name: ");
                controller.setFirstName(firstName);
            } catch (InvalidFormatException e) {
                MainView.showError(e.getMessage());
            }
        } while(firstName == null);

        do {
            try {
                lastName = MainView.promptForInput("Enter last name: ");
                controller.setLastName(lastName);
            } catch (InvalidFormatException e) {
                MainView.showError(e.getMessage());
            }
        } while(lastName == null);

        do {
            try {
                dob = MainView.promptForInput("Enter date of birth (dd/mm/yyyy): ");
                controller.setDateOfBirth(dob);
            } catch (InvalidFormatException e) {
                MainView.showError(e.getMessage());
            }
        } while(dob == null);

        do {
            try {
                emailAddress = MainView.promptForInput("Enter email address: ");
                controller.setEmailAddress(emailAddress);
            } catch (InvalidFormatException e) {
                MainView.showError(e.getMessage());
            }
        } while(emailAddress == null);

        do {
            try {
                username = MainView.promptForInput("Enter username: ");
                controller.setUsername(username);
            } catch (InvalidFormatException e) {
                MainView.showError(e.getMessage());
            }
        } while(username == null);

        do {
            try {
                password = MainView.promptForInput("Enter password: ");
                controller.setPassword(password);
            } catch (InvalidFormatException e) {
                MainView.showError(e.getMessage());
            }
        } while(password == null);
    }
}
