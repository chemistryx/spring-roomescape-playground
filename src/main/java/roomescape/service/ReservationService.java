package roomescape.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDao reservationDAO;
    private final TimeDao timeDao;

    public List<ReservationResponseDto> findAllReservations() {
        return List.copyOf(reservationDAO.findAll()
                .stream()
                .map(ReservationResponseDto::new)
                .collect(Collectors.toList())
        );
    }

    public ReservationResponseDto addReservation(ReservationRequestDto requestDto) {
        Time time = timeDao.findById(requestDto.getTimeId());
        Reservation reservation = new Reservation(
                null,
                requestDto.getName(),
                requestDto.getDate(),
                time
        );
        Reservation newReservation = reservationDAO.addReservation(reservation);
        return new ReservationResponseDto(newReservation);
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservationById(id);
    }
}
