package roomescape.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationResponseDto {

    private long id;

    private String name;

    private LocalDate date;

    @JsonProperty("time")
    private TimeResponseDto timeResponseDto;

    public ReservationResponseDto(final long id, final String name, final LocalDate date, final TimeResponseDto timeResponseDto) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeResponseDto = timeResponseDto;
    }
}
