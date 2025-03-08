package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.entity.Time;
import roomescape.exception.InvalidTimeRequestException;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Long save(LocalTime time) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time.toString());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Time findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM time WHERE id = ?", timeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidTimeRequestException("해당 ID의 시간을 찾을 수 없습니다: " + id);
        }
    }

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> new Time(
            rs.getLong("id"),
            rs.getObject("time", LocalTime.class)
    );
}
