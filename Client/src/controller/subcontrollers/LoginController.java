package controller.subcontrollers;

import controller.MainController;
import shared.User;
import view.View;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LoginController {
    private User user = new User();
    private final MainController mainController;
    private View view;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void createAccount() {
        view.getFirstName();
        view.getLastName();
        view.getDateOfBirth();
        view.getEmailAddress();
        view.getUsername();
        view.getPassword();

        mainController.doCreateNewUser(user);
    }

    public void setFirstName(String firstName) {
        if(firstName.isBlank()) {
            view.showError("First name cannot be blank");
            view.getFirstName();
        }
        user.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        if(lastName.isBlank()) {
            view.showError("Last name cannot be blank");
            view.getLastName();
        }
        user.setLastName(lastName);
    }

    public void setEmailAddress(String emailAddress) {
        if(!emailAddress.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            view.showError("Please enter a valid email address");
            view.getEmailAddress();
        }
        user.setEmailAddress(emailAddress);
    }

    public void setUsername(String username) {
        if(username.isBlank()) {
            view.showError("User name cannot be blank");
            view.getUsername();
        } else if(username.contains(" ")) {
            view.showError("Username cannot contain spaces");
            view.getUsername();
        }
        user.setUsername(username);
    }

    public void setPassword(String password) {
        if(password.isBlank()) {
            view.showError("Password cannot be blank");
            view.getPassword();
        }
        user.setPassword(password);
    }

    public void setDateOfBirth(String dob) {
        try {
            LocalDate date = LocalDate.parse(dob);
            user.setDateOfBirth(date);
        } catch (DateTimeParseException e) {
            view.showError("Please enter a valid date of birth");
            view.getDateOfBirth();
        }
    }

    public void login() {
        if(user.getUsername() == null || user.getPassword() == null) {
            view.showLoginMenu();
            return;
        }
        mainController.doLogin(user.getUsername(), user.getPassword());
    }

    public String getUserId() {
        return user.getUsername();
    }
}
