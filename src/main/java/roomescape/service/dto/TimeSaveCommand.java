package roomescape.service.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public record TimeSaveCommand(
        LocalTime time
) {

    public Time toEntity() {
        return new Time(time);
    }
}
