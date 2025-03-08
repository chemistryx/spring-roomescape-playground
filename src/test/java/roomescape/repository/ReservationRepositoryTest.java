package roomescape.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
public class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
        jdbcTemplate.update("INSERT INTO TIME (ID, TIME) VALUES (?, ?)", 1L, "10:00");
        jdbcTemplate.update("INSERT INTO TIME (ID, TIME) VALUES (?, ?)", 2L, "11:00");
    }

    @Test
    void testFindAll() {
        Long timeId1 = 1L;
        Long timeId2 = 2L;
        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = LocalDate.now().plusDays(2);
        reservationRepository.save("seongmin", date1, timeId1);
        reservationRepository.save("theo", date2, timeId2);

        List<Reservation> reservations = reservationRepository.findAll();

        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations)
                        .extracting("name", "reservationDate", "reservationTime.time")
                        .containsExactlyInAnyOrder(
                                tuple("seongmin", date1, LocalTime.of(10, 0)),
                                tuple("theo", date2, LocalTime.of(11, 0))
                        )
        );
    }

    @Test
    void testSave() {
        Long timeId = 1L;
        Long id = reservationRepository.save("seongmin", LocalDate.now().plusDays(1), timeId);
        assertAll(
                () -> assertThat(id).isNotNull(),
                () -> assertThat(reservationRepository.existsById(id)).isTrue()
        );
    }

    @Test
    void testDeleteById() {
        Long timeId = 1L;
        Long id = reservationRepository.save("seongmin", LocalDate.now().plusDays(1), timeId);
        reservationRepository.deleteById(id);
        assertThat(reservationRepository.existsById(id)).isFalse();
    }

    @Test
    void testExistsById() {
        Long timeId = 1L;
        Long id = reservationRepository.save("seongmin", LocalDate.now().plusDays(1), timeId);
        assertThat(reservationRepository.existsById(id)).isTrue();
        assertThat(reservationRepository.existsById(100L)).isFalse();
    }
}
