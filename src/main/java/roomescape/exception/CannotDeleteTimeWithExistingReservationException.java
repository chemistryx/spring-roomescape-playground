package roomescape.exception;

public class CannotDeleteTimeWithExistingReservationException extends RoomEscapeException {
    public CannotDeleteTimeWithExistingReservationException(String message) {
        super(message);
    }
}
