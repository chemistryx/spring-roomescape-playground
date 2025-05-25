package roomescape.controller.dto;

import roomescape.service.dto.TimeResult;

import java.time.LocalTime;

public record TimeResponse(
        Long id,
        LocalTime time
) {
    public static TimeResponse from(TimeResult result) {
        return new TimeResponse(result.id(), result.time());
    }
}
