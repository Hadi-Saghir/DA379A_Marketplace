package shared;

import java.io.Serializable;

public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    private int clientId;
    private String message;

    public Notification(int clientId, String message) {
        this.clientId = clientId;
        this.message = message;
    }

    public int getClientId() {
        return clientId;
    }

    public String getMessage() {
        return message;
    }
}
