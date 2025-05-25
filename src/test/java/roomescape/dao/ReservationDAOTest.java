package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class ReservationDAOTest {

    @Autowired
    ReservationDao reservationDao;
    @Autowired
    DataSource dataSource;
    @Autowired
    TimeDao timeDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void resetDatabase() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE reservation");
        jdbcTemplate.execute("TRUNCATE TABLE time");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @Test
    void connection() {
        try (final var connection = dataSource.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save() {
        Time time = new Time(LocalTime.parse("10:00"));
        Time savedTime = timeDao.save(time);
        Reservation reservation = new Reservation("name", LocalDate.parse("2026-05-02"), savedTime);
        Reservation savedReservation = reservationDao.save(reservation);

        assertThat(savedReservation.getId()).isGreaterThan(0L);
        assertThat(savedReservation.getName()).isEqualTo("name");
        assertThat(savedReservation.getDate()).isEqualTo(LocalDate.parse("2026-05-02"));
        assertThat(savedReservation.getTime().getId()).isEqualTo(savedTime.getId());
    }

    @Test
    void findById() {
        Time time = new Time(LocalTime.parse("10:00"));
        Time savedTime = timeDao.save(time);
        Reservation reservation = new Reservation("name", LocalDate.parse("2026-05-02"), savedTime);
        Reservation savedReservation = reservationDao.save(reservation);

        //when
        Reservation findReservation = reservationDao.findById(savedReservation.getId());

        //then
        assertThat(findReservation.getId()).isEqualTo(savedReservation.getId());
        assertThat(findReservation.getName()).isEqualTo(savedReservation.getName());
        assertThat(findReservation.getDate()).isEqualTo(savedReservation.getDate());
        assertThat(findReservation.getTime().getId()).isEqualTo(savedReservation.getTime().getId());
    }

    @Test
    void update() {
        //given
        Time time1 = new Time(LocalTime.parse("10:00"));
        Time time2 = new Time(LocalTime.parse("11:00"));
        Time savedTime1 = timeDao.save(time1);
        Time savedTime2  = timeDao.save(time2);

        Reservation reservation = new Reservation("name", LocalDate.parse("2026-05-02"), savedTime1);
        Reservation savedReservation = reservationDao.save(reservation);

        //when
        Reservation updateReservationParam = new Reservation("newName", LocalDate.parse("2026-05-03"), savedTime2);
        reservationDao.update(savedReservation.getId(), updateReservationParam);
        Reservation updatedReservation = reservationDao.findById(savedReservation.getId());

        //then
        assertThat(updatedReservation.getId()).isEqualTo(savedReservation.getId());
        assertThat(updatedReservation.getName()).isEqualTo("newName");
        assertThat(updatedReservation.getDate()).isEqualTo("2026-05-03");
        assertThat(updatedReservation.getTime().getId()).isEqualTo(savedTime2.getId());
    }

    @Test
    void delete() {
        //given
        Time time = new Time(LocalTime.parse("10:00"));
        Time savedTime = timeDao.save(time);
        Reservation reservation = new Reservation("name", LocalDate.parse("2026-05-02"), savedTime);
        Reservation savedReservation = reservationDao.save(reservation);

        //when
        reservationDao.delete(savedReservation.getId());

        //then
        assertThatThrownBy(() -> reservationDao.findById(savedReservation.getId()))
                .isInstanceOf(NotFoundReservationException.class);
    }
}
