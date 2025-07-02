package roomescape.domain;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private Time time;
}
