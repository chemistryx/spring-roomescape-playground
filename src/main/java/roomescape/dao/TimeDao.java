package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

@Repository
public class TimeDao implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Time> timeRowMapper() {
        return (rs, rowNum) -> new Time(
            rs.getLong("id"),
            rs.getTime("time").toLocalTime()
        );
    }

    @Override
    public Optional<Time> findById(Long id) {
        String sql = "SELECT id, time FROM time where id = ?";
        try {
            Time time = jdbcTemplate.queryForObject(sql, timeRowMapper(), id);
            return Optional.of(time);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper());
    }

    @Override
    public Time save(Time time) {
        String sql = "INSERT INTO time(time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, java.sql.Time.valueOf(time.getTime()));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Time(
            id,
            time.getTime()
        );
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
