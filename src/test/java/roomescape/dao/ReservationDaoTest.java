package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/schema.sql")
@Transactional
public class ReservationDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private ReservationDao reservationDao;
    private TimeDao timeDao;
    private Time fixedTime;

    @BeforeEach
    void setUp() {
        timeDao = new TimeDao(jdbcTemplate);
        reservationDao = new ReservationDao(jdbcTemplate);

        fixedTime = timeDao.save(new Time(null, LocalTime.of(10, 0)));

        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (:name, :date, :timeId)",
                new MapSqlParameterSource()
                        .addValue("name", "user1")
                        .addValue("date", "2025-05-21")
                        .addValue("timeId", fixedTime.getId())
        );
    }

    @Test
    @DisplayName("예약 추가 조회 테스트")
    void create_and_get_test() {
        Reservation reservation = new Reservation(null, "user1", LocalDate.of(2025,5,21), fixedTime);
        Reservation saved = reservationDao.addReservation(reservation);
        Reservation found = reservationDao.findById(saved.getId());

        assertThat(found.getName()).isEqualTo("user1");
        assertThat(found.getDate()).isEqualTo(LocalDate.of(2025,5,21));
        assertThat(found.getTime().getTime()).isEqualTo(LocalTime.of(10,0));
    }

    @Test
    @DisplayName("전체 예약 목록 조회 테스트")
    void get_all_test() {
        LocalDate date1 = LocalDate.of(2025, 5, 21);
        LocalDate date2 = LocalDate.of(2025, 5, 22);

        Reservation reservation1 = new Reservation(null, "user2", date1, fixedTime);
        Reservation reservation2 = new Reservation(null, "user3", date2, fixedTime);

        Reservation saved1 = reservationDao.addReservation(reservation1);
        Reservation saved2 = reservationDao.addReservation(reservation2);

        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations)
                .extracting("id")
                .contains(saved1.getId(), saved2.getId());
    }

    @Test
    @DisplayName("예약 수정 테스트")
    void update_test() {
        Reservation reservation = new Reservation(null, "수정전", LocalDate.of(2025, 5, 21), fixedTime);
        Reservation saved = reservationDao.addReservation(reservation);

        ReservationRequestDto updateDto = new ReservationRequestDto("수정후", LocalDate.of(2025, 6, 1), fixedTime.getId());
        reservationDao.updateReservation(saved.getId(), updateDto);

        Reservation updated = reservationDao.findById(saved.getId());

        assertThat(updated.getName()).isEqualTo("수정후");
        assertThat(updated.getDate()).isEqualTo(LocalDate.of(2025, 6, 1));
    }

    @Test
    @DisplayName("예약 삭제 테스트")
    void delete_test() {
        Reservation reservation = new Reservation(null, "삭제예정", LocalDate.now(), fixedTime);
        Reservation saved = reservationDao.addReservation(reservation);

        reservationDao.deleteReservationById(saved.getId());

        List<Reservation> all = reservationDao.findAll();
        assertThat(all).noneMatch(r -> r.getId().equals(saved.getId()));
    }
}
