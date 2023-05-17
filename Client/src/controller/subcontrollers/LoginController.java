package controller.subcontrollers;

import controller.InvalidFormatException;
import controller.MainController;
import Shared.Shared.src.User;
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
        view.getAccountDetails();
        if(user.getUsername() == null
                || user.getPassword() == null
                || user.getFirstName() == null
                || user.getLastName() == null
                || user.getEmailAddress() == null
                || user.getDateOfBirth() == null) {
            createAccount();
            return;
        }


        try {
            if(mainController.doCreateNewUser(user)) {
                view.showMessage("User created successfully");
            } else {
                view.showError("Failed to create user");
            }
        } catch(IOException ex) {
            view.connectionError(ex.getMessage());
        } catch(ClassNotFoundException ex) {
            view.parseError(ex.getMessage());
        }

        view.showLoginMenu();
    }

    public void setFirstName(String firstName) throws InvalidFormatException {
        if(firstName.length() < 2) {
            throw new InvalidFormatException("First name must be at least 2 characters long");
        }

        user.setFirstName(firstName);
    }

    public void setLastName(String lastName) throws InvalidFormatException {
        if(lastName.length() < 2) {
            throw new InvalidFormatException("Last name must be at least 2 characters long");
        }

        user.setLastName(lastName);
    }

    public void setEmailAddress(String emailAddress) throws InvalidFormatException {
        if(!emailAddress.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new InvalidFormatException("Please enter a valid email address");
        }

        user.setEmailAddress(emailAddress);
    }

    public void setUsername(String username) throws InvalidFormatException {
        if(username.length() < 2) {
            throw new InvalidFormatException("Username must be at least 2 characters long");
        }

        user.setUsername(username);
    }

    public void setPassword(String password) throws InvalidFormatException {
        if(password.length() < 2) {
            throw new InvalidFormatException("Password must be at least 2 characters long");
        }

        user.setPassword(password);
    }

    public void setDateOfBirth(String dob) throws InvalidFormatException {
        try {
            LocalDate date = LocalDate.parse(dob);
            user.setDateOfBirth(date);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Please enter a valid date of birth");
        }
    }

    public void login() {
        if(user.getUsername() == null || user.getPassword() == null) {
            view.showLoginMenu();
            return;
        }

        try {
            if(mainController.doLogin(user.getUsername(), user.getPassword())) {
                view.showMainMenu();
            } else {
                view.showError("Invalid username or password");
            }
        } catch(IOException ex) {
            view.connectionError(ex.getMessage());
        } catch(ClassNotFoundException ex) {
            view.parseError(ex.getMessage());
        }
    }
}
