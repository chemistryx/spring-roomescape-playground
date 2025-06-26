package roomescape.core.exception;

public class ErrorResponse {

    private final int status;
    private final String message;

    private ErrorResponse(final int status, final String errorMessage) {
        this.status = status;
        this.message = errorMessage;
    }

    public static ErrorResponse of(CustomErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus(), errorCode.getMessage());
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
