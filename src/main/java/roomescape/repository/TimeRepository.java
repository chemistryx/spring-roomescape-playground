package roomescape.repository;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

@Repository
public class TimeRepository {
    private static final String TABLE_NAME = "time";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Time> timeMapper = (rs, rowNum) -> {
        return Time.of(
                rs.getInt("id"),
                rs.getTime("time").toLocalTime()
        );
    };

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAll() {
        String query = "SELECT id, time FROM " + TABLE_NAME;

        return jdbcTemplate.query(query, timeMapper);
    }

    public Time save(Time time) {
        Map<String, Object> params = Map.of(
                "time", time.time()
        );

        Number id = simpleJdbcInsert.executeAndReturnKey(params);

        return Time.of(id.intValue(), time.time());
    }

    public void deleteById(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        jdbcTemplate.update(query, id);
    }
}
