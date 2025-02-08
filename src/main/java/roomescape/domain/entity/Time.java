package roomescape.domain.entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
    private final Long id;
    private final LocalTime time;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static Time of(String time) {
        return new Time(null, LocalTime.parse(time, TIME_FORMATTER));
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
