package Shared;

public class Notification {
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
