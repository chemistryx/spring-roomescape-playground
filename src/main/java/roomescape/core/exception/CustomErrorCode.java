package roomescape.core.exception;

public enum CustomErrorCode {

  RESERVATION_ID_REQUIRED(400, "예약 ID는 필수입니다."),
  RESERVATION_ID_INVALID(400, "예약 ID는 양수여야 합니다."),

  RESERVATION_ID_NOT_FOUND(404, "존재하지 않는 예약 정보입니다."),

  RESERVATION_DATE_REQUIRED(400, "예약 날짜는 필수입니다."),
  RESERVATION_NAME_REQUIRED(400, "예약자 이름은 필수입니다."),
  RESERVATION_NAME_EMPTY(400, "예약자 이름은 공백일 수 없습니다."),
  RESERVATION_TIME_REQUIRED(400, "예약 시간은 필수입니다."),
  RESERVATION_DATE_PAST(404, "예약 날짜는 과거가 될 수 없습니다."),

  INTERNAL_SERVER_ERROR(500, "서버 오류 입니다.");

  private final int status;
  private final String message;

  CustomErrorCode(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
