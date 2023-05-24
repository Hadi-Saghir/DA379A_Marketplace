package controller.subcontrollers;

import controller.MainController;
import shared.User;
import view.View;

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

    public void login(String username, String password) {
        if(user.getUsername() == null || user.getPassword() == null) {
            view.showLoginMenu();
            return;
        }
        mainController.doLogin(user.getUsername(), user.getPassword());
    }

    public boolean setFirstName(String firstName) {
        if(firstName.isBlank()) {
            mainController.setLatestError("First name cannot be blank");
            return false;
        } else {
            user.setFirstName(firstName);
            return true;
        }
    }

    public boolean setLastName(String lastName) {
        if(lastName.isBlank()) {
            mainController.setLatestError("Last name cannot be blank");
            return false;
        } else {
            user.setLastName(lastName);
            return true;
        }
    }

    public boolean setEmailAddress(String emailAddress) {
        if(!emailAddress.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            mainController.setLatestError("Not a valid email address");
            return false;
        } else {
            user.setEmailAddress(emailAddress);
            return true;
        }
    }

    public boolean setUsername(String username) {
        if(username.isBlank()) {
            mainController.setLatestError("User name cannot be blank");
            return false;
        } else if(username.contains(" ")) {
            mainController.setLatestError("Username cannot contain spaces");
            return false;
        } else {
            user.setUsername(username);
            return true;
        }
    }

    public boolean setPassword(String password) {
        if(password.isBlank()) {
            mainController.setLatestError("Password cannot be blank");
            return false;
        } else {
            user.setPassword(password);
            return true;
        }
    }

    public boolean setDateOfBirth(String dob) {
        try {
            LocalDate date = LocalDate.parse(dob);
            user.setDateOfBirth(date);
            return true;
        } catch (DateTimeParseException e) {
            mainController.setLatestError("Please enter a valid date of birth");
            return false;
        }
    }

    public User getUser() {
        return user;
    }

    public String getUserId() {
        return user.getUsername();
    }

}
