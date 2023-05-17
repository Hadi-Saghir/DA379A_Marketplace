import java.io.Serializable;

public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String message;

    public Notification(String clientId, String message) {
        this.clientId = clientId;
        this.message = message;
    }

    public String getClientId() {
        return clientId;
    }

    public String getMessage() {
        return message;
    }
}
