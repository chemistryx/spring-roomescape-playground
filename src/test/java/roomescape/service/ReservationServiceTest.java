package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.Time;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Reservation reservation = new Reservation(1L, "seongmin", LocalDate.now().plusDays(1), new Time(1L, LocalTime.of(10, 0)));
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        List<ReservationResponse> responses = reservationService.findAll();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).name()).isEqualTo("seongmin");
    }

    @Test
    void testSave() {
        Time time = new Time(1L, LocalTime.of(10, 0));
        ReservationRequest request = new ReservationRequest(LocalDate.now().plusDays(1), "seongmin", 1L);
        when(timeRepository.findById(anyLong())).thenReturn(time);
        when(reservationRepository.save(anyString(), any(LocalDate.class), anyLong())).thenReturn(1L);

        ReservationResponse response = reservationService.save(request);

        assertThat(response.name()).isEqualTo("seongmin");
        assertThat(response.date()).isEqualTo(LocalDate.now().plusDays(1));
    }

    @Test
    void testDeleteById() {
        when(reservationRepository.existsById(anyLong())).thenReturn(true);

        reservationService.deleteById(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(reservationRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> reservationService.deleteById(1L))
                .isInstanceOf(InvalidReservationRequestException.class)
                .hasMessage("이미 삭제된 예약입니다.");
    }
}
