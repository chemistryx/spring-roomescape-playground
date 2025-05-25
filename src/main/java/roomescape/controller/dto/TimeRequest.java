package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import roomescape.service.dto.TimeSaveCommand;

import java.time.LocalTime;

public class TimeRequest {

    private final LocalTime time;

    @JsonCreator
    public TimeRequest(LocalTime time) {
        validate(time);
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public TimeSaveCommand toCommand() {
        return new TimeSaveCommand(time);
    }

    private void validate(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }
}
