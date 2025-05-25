package roomescape.service.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;

public record ReservationSaveCommand(
        String name,
        LocalDate date,
        Long timeId
) {
    public Reservation toEntity(Time time) {
        return new Reservation(name, date, time);
    }
}
