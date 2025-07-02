package roomescape.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class TimeDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public TimeDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> new Time(
            resultSet.getLong("id"),
            LocalTime.parse(resultSet.getString("time_value"))
    );

    public Time save(Time time) {
        String sql = "INSERT INTO time (time_value) VALUES (:timeValue)";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(time);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(sql, param, keyHolder, new String[]{"id"});

        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("시간 ID 생성 실패"));
        return findById(id);
    }

    public boolean existsByTime(LocalTime time) {
        String sql = "SELECT COUNT(*) FROM time WHERE time_value = :timeValue";
        MapSqlParameterSource param = new MapSqlParameterSource("timeValue", time);
        Integer count = namedJdbcTemplate.queryForObject(sql, param, Integer.class);
        return count != null && count > 0;
    }

    public List<Time> findAll() {
        String sql = "SELECT * FROM time ORDER BY id";
        return namedJdbcTemplate.query(sql, timeRowMapper);
    }

    public Time findById(Long id) {
        String sql = "SELECT * FROM time WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        return namedJdbcTemplate.queryForObject(sql, param, timeRowMapper);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        namedJdbcTemplate.update(sql, param);
    }
}
