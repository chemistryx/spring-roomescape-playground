package roomescape.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;
import roomescape.exception.reservation.NotReservationFoundException;
import roomescape.exception.reservation.ReservationInsertFailedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public ReservationDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper =
            (resultSet, rowNum) -> new Reservation(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")),
                    new Time(
                            resultSet.getLong("time_id"),
                            LocalTime.parse(resultSet.getString("time_value"))
                    )
            );

    public Reservation addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (:name, :date, :timeId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate().toString())
                .addValue("timeId", reservation.getTime().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new ReservationInsertFailedException("예약 ID 생성에 실패했습니다."));
        return findById(id);
    }

    public Reservation findById(Long id) {
        String sql = """
                    SELECT 
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        r.time_id,
                        t.time_value as time_value
                    FROM reservation r
                    INNER JOIN time t ON r.time_id = t.id
                    WHERE r.id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return namedJdbcTemplate.queryForObject(sql, params, reservationRowMapper);
    }

    public List<Reservation> findAll() {
        String sql = """
                    SELECT 
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        r.time_id,
                        t.time_value as time_value
                    FROM reservation r
                    INNER JOIN time t ON r.time_id = t.id
                """;

        return namedJdbcTemplate.query(sql, reservationRowMapper);
    }

    public void updateReservation(Long id, ReservationRequestDto dto) {
        String sql = "UPDATE reservation SET name = :name, date = :date, time_id = :timeId WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", dto.getName())
                .addValue("date", dto.getDate().toString())
                .addValue("timeId", dto.getTimeId());

        namedJdbcTemplate.update(sql, params);
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        int result = namedJdbcTemplate.update(sql, params);
        if (result == 0) {
            throw new NotReservationFoundException("존재하지 않는 예약입니다.");
        }
    }

    public boolean existsByTimeId(Long timeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE time_id = :timeId";
        MapSqlParameterSource params = new MapSqlParameterSource("timeId", timeId);

        Integer count = namedJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

}
