package controller.subcontrollers;

import controller.MainController;
import shared.User;
import view.View;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Controller for the login.
 */
public class LoginController {
    private User user = new User();
    private final MainController mainController;
    private View view;

    /**
     * Constructor for LoginController.
     * @param mainController MainController.
     */
    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Sets the view for the LoginController.
     * @param view View.
     */
    public void setView(View view) {
        this.view = view;
    }


    /**
     * Sets the user's username and password and calls the mainController to login.
     * @param username Username of the user to login.
     * @param password Password of the user to login.
     */
    public void login(String username, String password) {
        setUsername(username);
        setPassword(password);
        mainController.doLogin(user.getUsername(), user.getPassword());
    }

    /**
     * Sets the user's first name.
     * @param firstName First name of the user.
     * @return True if the first name is valid, false otherwise.
     */
    public boolean setFirstName(String firstName) {
        if(firstName.isBlank()) {
            mainController.setLatestError("First name cannot be blank");
            return false;
        } else {
            user.setFirstName(firstName);
            return true;
        }
    }

    /**
     * Sets the user's last name.
     * @param lastName Last name of the user.
     * @return True if the last name is valid, false otherwise.
     */
    public boolean setLastName(String lastName) {
        if(lastName.isBlank()) {
            mainController.setLatestError("Last name cannot be blank");
            return false;
        } else {
            user.setLastName(lastName);
            return true;
        }
    }

    /**
     * Sets the user's email address.
     * @param emailAddress Email address of the user.
     * @return True if the email address is valid, false otherwise.
     */
    public boolean setEmailAddress(String emailAddress) {
        if(!emailAddress.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            mainController.setLatestError("Not a valid email address");
            return false;
        } else {
            user.setEmailAddress(emailAddress);
            return true;
        }
    }

    /**
     * Sets the user's username.
     * @param username Username of the user.
     * @return True if the username is valid, false otherwise.
     */
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

    /**
     * Sets the user's password.
     * @param password Password of the user.
     * @return True if the password is valid, false otherwise.
     */
    public boolean setPassword(String password) {
        if(password.isBlank()) {
            mainController.setLatestError("Password cannot be blank");
            return false;
        } else {
            user.setPassword(password);
            return true;
        }
    }

    /**
     * Sets the user's date of birth.
     * @param dob Date of birth of the user.
     * @return True if the date of birth is a valid date, false otherwise.
     */
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

    /**
     * Returns the current user.
     * @return The current user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the user's username.
     * @return Username of the current user.
     */
    public String getUserId() {
        return user.getUsername();
    }

}
