package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.domain.dto.TimeRequest;
import roomescape.domain.dto.TimeResponse;
import roomescape.domain.entity.Time;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TimeServiceTest {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private TimeService timeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Time time = new Time(1L, LocalTime.of(10, 0));
        when(timeRepository.findAll()).thenReturn(List.of(time));

        List<TimeResponse> times = timeService.findAll();

        assertThat(times).hasSize(1);
        assertThat(times.get(0).time()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void testSave() {
        TimeRequest request = new TimeRequest(LocalTime.of(10, 0));
        when(timeRepository.save(any(LocalTime.class))).thenReturn(1L);

        TimeResponse response = timeService.save(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.time()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void testDeleteById() {
        doNothing().when(timeRepository).deleteById(anyLong());

        timeService.deleteById(1L);

        verify(timeRepository, times(1)).deleteById(1L);
    }
}
