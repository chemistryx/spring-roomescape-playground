package roomescape.core.exception;

public class ApplicationException extends RuntimeException {

  private final CustomErrorCode customErrorCode;

  public ApplicationException(CustomErrorCode customErrorCode) {
    super();
    this.customErrorCode = customErrorCode;
  }

  public CustomErrorCode getCustomErrorCode() {
    return customErrorCode;
  }
}
