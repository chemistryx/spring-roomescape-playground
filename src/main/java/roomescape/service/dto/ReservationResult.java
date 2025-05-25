package roomescape.service.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationResult(
        Long id,
        String name,
        LocalDate date,
        TimeResult time
) {
    public static ReservationResult from(Reservation reservation) {
        return new ReservationResult(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                TimeResult.from(reservation.getTime()));
    }
}
