package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.time.LocalTime;

@Data
@Getter
public class TimeRequestDto {

    @NotNull(message = "예약 시간은 필수 입력 항목입니다.")
    private LocalTime timeValue;
}
