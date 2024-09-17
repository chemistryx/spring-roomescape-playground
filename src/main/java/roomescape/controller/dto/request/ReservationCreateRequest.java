package roomescape.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationCreateRequest(
    @NotBlank
    String name,

    @NotBlank
    String date,

    @NotNull
    Long time
) {
}
