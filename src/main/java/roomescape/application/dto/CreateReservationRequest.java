package roomescape.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.core.exception.ApplicationException;
import roomescape.core.exception.CustomErrorCode;
import roomescape.domain.model.Reservation;

public record CreateReservationRequest(LocalDate date, String name, LocalTime time) {

  public CreateReservationRequest {
    validateReservationInfo(date, name, time);
  }

  private void validateReservationInfo(LocalDate date, String name, LocalTime time) {
    validateDate(date);
    validateName(name);
    validateTime(time);
  }

  private void validateDate(LocalDate date) {
    if (date == null) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_DATE_REQUIRED);
    }

    if (date.isBefore(LocalDate.now())) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_DATE_PAST);
    }
  }

  private static void validateName(String name) {
    if (name == null) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_NAME_REQUIRED);
    }

    if (name.trim().isEmpty()) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_NAME_EMPTY);
    }
  }

  private static void validateTime(LocalTime time) {
    if (time == null) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_TIME_REQUIRED);
    }
  }

  public Reservation toReservation(Long id) {
    return new Reservation(id, name, date, time);
  }
}
