package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleUnknownException(Exception e) {
        ErrorResult errorResult = new ErrorResult("INTERNAL_SERVER_ERROR", "예상치 못한 오류 발생");
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResult> handleJsonParseError(HttpMessageNotReadableException e) {
        ErrorResult errorResult = new ErrorResult("INVALID_JSON", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> illegalExceptionHandler(IllegalArgumentException e) {
        ErrorResult errorResult = new ErrorResult("BAD_REQUEST", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResult> notFoundReservationHandler(NotFoundReservationException e) {
        ErrorResult errorResult = new ErrorResult("NOT_FOUND_RESERVATION", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationAlreadyExistsException.class)
    public ResponseEntity<ErrorResult> AlreadyExistsHandler(ReservationAlreadyExistsException e) {
        ErrorResult errorResult = new ErrorResult("RESERVATION_ALREADY_EXISTS", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidReservationDateException.class)
    public ResponseEntity<ErrorResult> AlreadyExistsHandler(InvalidReservationDateException e) {
        ErrorResult errorResult = new ErrorResult("INVALID_RESERVATION_DATE", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotDeleteTimeWithExistingReservationException.class)
    public ResponseEntity<ErrorResult> handleCannotDeleteTime(CannotDeleteTimeWithExistingReservationException e) {
        ErrorResult errorResult = new ErrorResult("CANNOT_DELETE_RESERVED_TIME", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequiredFieldException.class)
    public ResponseEntity<ErrorResult> handleCannotDeleteTime(MissingRequiredFieldException e) {
        ErrorResult errorResult = new ErrorResult("MISSING_REQUIRED_FIELD", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateTimeException.class)
    public ResponseEntity<ErrorResult> handleCannotDeleteTime(DuplicateTimeException e) {
        ErrorResult errorResult = new ErrorResult("DUPLICATE_TIME", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity<ErrorResult> handleCannotDeleteTime(RoomEscapeException e) {
        ErrorResult errorResult = new ErrorResult(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(errorResult, e.getHttpStatus());
    }

}
