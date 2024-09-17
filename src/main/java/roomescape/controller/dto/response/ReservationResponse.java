package roomescape.controller.dto.response;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationResponse(
    Long id,

    String name,

    String date,

    Time time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            new Time(
                reservation.getTime().getId(),
                reservation.getTime().getTime()
            )
        );
    }
}
