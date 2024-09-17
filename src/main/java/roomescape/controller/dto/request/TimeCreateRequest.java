package roomescape.controller.dto.request;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;

public record TimeCreateRequest(
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime time
) {
}
