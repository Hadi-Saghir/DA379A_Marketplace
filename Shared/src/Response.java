import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record Response(ResponseType RESPONSE_TYPE, List<String> MESSAGE) implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    public enum ResponseType {
        SUCCESS,
        FAILURE;
    }
}
