package roomescape.controller.dto;

import roomescape.service.dto.ReservationResult;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        TimeResponse time
) {

    public static ReservationResponse from(ReservationResult result) {
        return new ReservationResponse(
                result.id(),
                result.name(),
                result.date(),
                TimeResponse.from(result.time())
        );
    }
}
