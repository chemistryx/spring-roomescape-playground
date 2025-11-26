package roomescape.model;

import java.time.LocalTime;

public record Time(Integer id, LocalTime time) {
    public static Time of(Integer id, LocalTime time) {
        return new Time(id, time);
    }

    public static Time create(LocalTime time) {
        return new Time(null, time);
    }
}
