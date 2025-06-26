package roomescape.application.dto;

import roomescape.core.exception.ApplicationException;
import roomescape.core.exception.CustomErrorCode;

public record DeleteReservationRequest(Long reservationId) {

  public DeleteReservationRequest {
    validateReservationId(reservationId);
  }

  private void validateReservationId(Long reservationId) {
    if (reservationId == null) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_ID_REQUIRED);
    }

    if (reservationId <= 0) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_ID_INVALID);
    }
  }
}
