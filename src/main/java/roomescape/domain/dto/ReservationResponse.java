package roomescape.domain.dto;

import roomescape.domain.entity.Reservation;

import java.time.LocalDate;

public record ReservationResponse(long id, String name, LocalDate date, TimeResponse time) {
    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getReservationDate(), new TimeResponse(reservation.getReservationTime()));
    }
}
