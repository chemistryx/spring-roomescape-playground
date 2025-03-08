package roomescape.domain.dto;

import java.time.LocalDate;

public record ReservationRequest(LocalDate date, String name, Long timeId) {
}
