package roomescape.exception;

import org.springframework.http.HttpStatus;

public class RoomEscapeException extends RuntimeException {

  private final String errorCode;
  private final HttpStatus httpStatus;

  protected RoomEscapeException(String message) {
    this(message, "ROOM_ESCAPE_ERROR", HttpStatus.BAD_REQUEST);
  }

  public RoomEscapeException(String message, String errorCode, HttpStatus httpStatus) {
    super(message);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
