package roomescape.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.model.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {

  public static ReservationResponse from(Reservation reservation) {
    return new ReservationResponse(
        reservation.getId(),
        reservation.getName(),
        reservation.getDate(),
        reservation.getTime()
    );
  }
}
