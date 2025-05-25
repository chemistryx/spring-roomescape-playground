package roomescape.domain;

import roomescape.exception.InvalidReservationDateException;

import java.time.LocalDate;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, Time time) {
        validateName(name);
        validateDate(date);
        validateTime(time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, Time time) {
        this(null, name, date, time);
    }

    public Reservation withId(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("예약 날짜는 오늘 이후여야 합니다.");
        }
    }

    private void validateTime(Time time) {
        if (time == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }
}
