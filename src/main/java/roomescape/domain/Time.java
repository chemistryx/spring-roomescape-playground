package roomescape.domain;

import lombok.*;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Time {

    private Long id;
    private LocalTime timeValue;
}
