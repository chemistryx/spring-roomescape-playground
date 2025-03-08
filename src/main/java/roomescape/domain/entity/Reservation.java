package roomescape.domain.entity;

import roomescape.exception.InvalidReservationRequestException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate reservationDate;
    private final Time reservationTime;

    public Reservation(Long id, String name, LocalDate reservationDate, Time reservationTime) {
        validate(name, reservationDate, reservationTime);
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    private void validate(String name, LocalDate reservationDate, Time reservationTime) {
        if (name == null || name.isBlank()) {
            throw new InvalidReservationRequestException("이름은 필수 입력 값입니다.");
        }

        if (reservationDate == null) {
            throw new InvalidReservationRequestException("예약 날짜는 필수 입력 값입니다.");
        }

        if (reservationTime == null) {
            throw new InvalidReservationRequestException("예약 시간은 필수 입력 값입니다.");
        }

        if (reservationDate.isBefore(LocalDate.now()) ||
                (reservationDate.isEqual(LocalDate.now()) && reservationTime.isBefore(LocalTime.now()))) {
            throw new InvalidReservationRequestException("과거로는 예약할 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public Time getReservationTime() {
        return reservationTime;
    }
}
