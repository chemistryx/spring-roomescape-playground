package roomescape.domain.dto;

import roomescape.domain.entity.Time;

import java.time.LocalDate;

public class ReservationRequest {
    private final LocalDate date;
    private final String name;
    private final Time time;

    public ReservationRequest(LocalDate date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = Time.of(time);
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }
}
