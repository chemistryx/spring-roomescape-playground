package roomescape.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeRequestDto {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

}
