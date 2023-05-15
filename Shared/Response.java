package Shared;

import java.io.Serial;
import java.io.Serializable;

public record Response(ResponseType RESPONSE_TYPE, String MESSAGE) implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    public enum ResponseType {
        SUCCESS,
        FAILURE;
    }
}
