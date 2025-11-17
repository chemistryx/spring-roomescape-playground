package roomescape.exception;

public class InvalidReservationRequestException extends IllegalArgumentException {
    public InvalidReservationRequestException(String message) {
        super(message);
    }
}
