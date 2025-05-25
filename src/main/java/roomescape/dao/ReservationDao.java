package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.NotFoundReservationException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static java.sql.Date.*;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setDate(2, valueOf(reservation.getDate()));
            preparedStatement.setLong(3, reservation.getTime().getId());
            return preparedStatement;
        }, keyHolder);

        return reservation.withId(keyHolder.getKey().longValue());
    }

    public Reservation findById(Long id) {
        try {
            String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.time
                    FROM reservation as r join time as t on r.time_id = t.id
                    WHERE r.id = ?
                    """;
            return jdbcTemplate.queryForObject(
                    sql,
                    reservationRowMapper(),
                    id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException("해당 id의 예약이 존재하지 않습니다. id = " + id);
        }
    }

    public boolean existsByTimeId(Long timeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE time_id = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, timeId);

        return count != null && count > 0;

    }

    public void update(Long id, Reservation reservation) {
        String sql = """
                UPDATE reservation 
                SET name = ?, date = ?, time_id = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getId(),
                id
        );
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.time
                FROM reservation as r join time as t on r.time_id = t.id
                """;

        return jdbcTemplate.query(
                sql,
                reservationRowMapper()
        );
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int update = jdbcTemplate.update(sql, id);

        if (update == 0) {
            throw new NotFoundReservationException("삭제할 예약이 없습니다. id = " + id);
        }
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            Time time = new Time(
                    resultSet.getLong("time_id"),
                    resultSet.getTime("time").toLocalTime()
            );

            return new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    time
            );
        };
    }
}
