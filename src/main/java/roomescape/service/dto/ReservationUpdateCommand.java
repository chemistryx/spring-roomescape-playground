package roomescape.service.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;

public record ReservationUpdateCommand(
        String name,
        LocalDate date,
        Time time
) {
    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
