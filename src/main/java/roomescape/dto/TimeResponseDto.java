package roomescape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TimeResponseDto {

    private Long id;
    private LocalTime timeValue;
}
