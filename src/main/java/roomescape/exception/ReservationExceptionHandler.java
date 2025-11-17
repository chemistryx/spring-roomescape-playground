package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler({
            InvalidReservationRequestException.class,
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,              
            MethodArgumentTypeMismatchException.class,          
            MissingServletRequestParameterException.class      
    })
    public ResponseEntity<Void> handleBadRequest(RuntimeException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFound(NotFoundReservationException e) {
        return ResponseEntity.status(404).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleUnexpected(Exception e) {
        return ResponseEntity.status(500).build();
    }
}
