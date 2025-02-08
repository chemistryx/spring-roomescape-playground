package roomescape.domain.entity;

import java.time.LocalDate;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate reservationDate;
    private final Time reservationTime;

    public Reservation(Long id, String name, LocalDate reservationDate, Time reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public Time getReservationTime() {
        return reservationTime;
    }
}
