package kong.portfolio.common.exception;

import kong.portfolio.common.status.StatusCode;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {
    private final StatusCode statusCode;
    private final Object data;

    public RestApiException(final StatusCode statusCode, final Object data) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
        this.data = data;
    }

    public RestApiException(final StatusCode statusCode) {
        this(statusCode, null);
    }
}