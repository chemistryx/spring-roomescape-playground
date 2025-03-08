package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.entity.Time;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@JdbcTest
public class TimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        timeRepository = new TimeRepository(jdbcTemplate);
    }

    @Test
    void testFindAll() {
        for (long i = 1; i <= 5; i++) {
            jdbcTemplate.update("INSERT INTO TIME (ID, TIME) VALUES (?, ?)", i, String.format("%02d:00", i % 24));
        }

        List<Time> times = timeRepository.findAll();

        assertThat(times)
                .hasSize(5)
                .extracting(Time::getId, Time::getTime)
                .containsExactly(
                        tuple(1L, LocalTime.of(1, 0)),
                        tuple(2L, LocalTime.of(2, 0)),
                        tuple(3L, LocalTime.of(3, 0)),
                        tuple(4L, LocalTime.of(4, 0)),
                        tuple(5L, LocalTime.of(5, 0))
                );
    }

    @Test
    void testSave() {
        Long id = timeRepository.save(LocalTime.of(10, 0));
        assertThat(id).isNotNull();
        Time savedTime = timeRepository.findById(id);
        assertThat(savedTime.getId()).isEqualTo(id);
    }

    @Test
    void testDeleteById() {
        jdbcTemplate.update("INSERT INTO TIME (ID, TIME) VALUES (?, ?)", 1L, "10:00");
        timeRepository.deleteById(1L);
        List<Time> times = timeRepository.findAll();
        assertThat(times).isEmpty();
    }

    @Test
    void testFindById() {
        jdbcTemplate.update("INSERT INTO time (id, time) VALUES (?, ?)", 1L, "10:00");
        Time time = timeRepository.findById(1L);
        assertThat(time).isNotNull();
        assertThat(time.getTime()).isEqualTo(LocalTime.of(10, 0));
    }
}
