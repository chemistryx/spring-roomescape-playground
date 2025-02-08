package roomescape.valid;

import roomescape.domain.dto.ReservationRequest;
import roomescape.exception.InvalidReservationRequestException;

public class ReservationValidator {

    public static void validate(ReservationRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidReservationRequestException("이름은 필수 입력 값입니다.");
        }

        if (request.getDate() == null) {
            throw new InvalidReservationRequestException("예약 날짜는 필수 입력 값입니다.");
        }

        if (request.getTime() == null) {
            throw new InvalidReservationRequestException("예약 시간은 필수 입력 값입니다.");
        }
    }
}
