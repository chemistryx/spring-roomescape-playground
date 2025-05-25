package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time save(Time time) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(time);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return time.withId(key.longValue());
    }

    public Optional<Time> findById(Long id) {
        String sql = "SELECT * FROM time WHERE id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    sql,
                    timeRowMapper(),
                    id
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Time> findByTime(LocalTime time) {
        String sql = "SELECT * FROM time WHERE time = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, timeRowMapper(), time));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Time> findAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(
                sql,
                timeRowMapper()
        );
    }

    public void delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Time> timeRowMapper() {
        return (resultSet, rowNum) -> new Time(
                resultSet.getLong("id"),
                resultSet.getTime("time").toLocalTime()
        );
    }
}
