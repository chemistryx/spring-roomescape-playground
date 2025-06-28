package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.entity.Time;

import java.util.List;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private static final RowMapper<Time> timeRowMapper =
            (resultSet, rowNum) -> Time.builder()
                    .id(resultSet.getLong("id"))
                    .time(resultSet.getTime("time").toLocalTime())
                    .build();

    public TimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingColumns("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time save(Time time) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(time);
        long id = simpleJdbcInsert.executeAndReturnKey(param).longValue();
        return Time.builder()
                .id(id)
                .time(time.getTime())
                .build();
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("select id, time from time", timeRowMapper);
    }

    public Time findById(long id) {
        String sql = "select id, time from time where id = ?";
        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("delete from time where id = ? ", id);
    }
}
