package roomescape.domain.dto;

import roomescape.domain.entity.Time;

import java.time.LocalTime;

public record TimeResponse(long id, LocalTime time) {
    public TimeResponse(Time time) {
        this(time.getId(), time.getTime());
    }
}
