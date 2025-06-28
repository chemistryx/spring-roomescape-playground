package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.entity.Reservation;
import roomescape.model.entity.Time;

import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;


    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {

        Time time = Time.builder()
                .time(resultSet.getTime("time_value").toLocalTime())
                .id(resultSet.getLong("time_id"))
                .build();

        Reservation reservation = Reservation.builder()
                .id(resultSet.getLong("reservation_id"))
                .name(resultSet.getString("name"))
                .date(resultSet.getDate("date").toLocalDate())
                .time(time)
                .build();

        return reservation;
    };

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
                select
                r.id as reservation_id,
                r.name as reservation_name,
                r.date as reservation_date,
                t.id as time_id,
                t.time as time_value
                from reservation as r inner join time as t on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation findById(long id) {
        String sql = """
                select
                r.id as reservation_id,
                r.name as reservation_name,
                r.date as reservation_date,
                t.id as time_id,
                t.time as time_value
                from reservation as r inner join time as t on r.time_id = t.id
                where r.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public Reservation save(final Reservation reservation) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate().toString())
                .addValue("time_id", reservation.getTime().getId());

        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Reservation.builder()
                .id(id)
                .date(reservation.getDate())
                .time(reservation.getTime())
                .name(reservation.getName())
                .build();
    }


    public int deleteById(final Long id) {
        int result = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return result;
    }
}
