package Shared;

import java.io.Serializable;

public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String message;

    public Notification(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
