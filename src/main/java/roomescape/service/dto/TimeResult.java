package roomescape.service.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public record TimeResult(
        Long id,
        LocalTime time
) {

    public static TimeResult from(Time time) {
        return new TimeResult(
                time.getId(),
                time.getTime());
    }
}
