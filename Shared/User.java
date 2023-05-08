package Shared;

import java.time.LocalDate;
import java.util.Collection;

public class User {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String emailAddress;
    private String username;
    private String password;

    public User(String firstName, String lastName, LocalDate dateOfBirth, String emailAddress, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    public String getPassword() {
        return password;
    }


}

