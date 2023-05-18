package shared;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record Response(ResponseType RESPONSE_TYPE,ResponseResult RESPONSE_RESULT, List<Object> MESSAGE) implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    public enum ResponseResult {
        SUCCESS,
        FAILURE;
    }

    // request types
    public enum ResponseType {
        REGISTER,
        LOGIN,
        ADD_PRODUCT,
        ALL_PRODUCTS,
        SEARCH_PRODUCT,
        SELL_PRODUCT,
        MAKE_OFFER,
        REGISTER_INTEREST,
        GET_PURCHASE_HISTORY,
        NOTIFICATION;
    }
}
