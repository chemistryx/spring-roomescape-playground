package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.exception.NotFoundTimeException;
import roomescape.service.dto.ReservationSaveCommand;

import java.time.LocalDate;

@SpringBootTest
class ReservationServiceTest {

    @Autowired ReservationService reservationService;

    @Test
    void time이_존재하지_않을경우_예외를_던진다() {
        //given
        ReservationSaveCommand command = new ReservationSaveCommand("이름", LocalDate.parse("2025-09-09"), 2L);

        //then
        Assertions.assertThatThrownBy(() -> reservationService.save(command))
                .isInstanceOf(NotFoundTimeException.class)
                .hasMessage("존재하지 않는 시간입니다. id = " + command.timeId());
    }
}
