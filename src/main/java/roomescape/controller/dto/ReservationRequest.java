package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.service.dto.ReservationSaveCommand;

import java.time.LocalDate;

public class ReservationRequest {
    private final String name;
    private final LocalDate date;
    private final Long timeId;

    public ReservationRequest(String name, LocalDate date, @JsonProperty("time") Long timeId) {
        validate(name, date, timeId);
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public ReservationSaveCommand toCommand() {
        return new ReservationSaveCommand(name, date, timeId);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTime() {
        return timeId;
    }

    private void validate(String name, LocalDate date, Long timeId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
        if (timeId == null || timeId <= 0) {
            throw new IllegalArgumentException("옳바르지 않은 id 값입니다");
        }
    }
}

