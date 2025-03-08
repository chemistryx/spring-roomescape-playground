package roomescape.domain.entity;

import roomescape.exception.InvalidTimeRequestException;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    private void validate(LocalTime time) {
        if (time == null) {
            throw new InvalidTimeRequestException("시간은 필수 입력 값입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public boolean isBefore(LocalTime now) {
        return time.isBefore(now);
    }
}
