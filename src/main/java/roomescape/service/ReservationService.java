package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roomescape.controller.dto.request.ReservationCreateRequest;
import roomescape.controller.dto.response.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(
        ReservationRepository reservationRepository,
        TimeRepository timeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @Transactional
    public ReservationResponse createReservation(
        ReservationCreateRequest request
    ) {
        Time time = timeRepository.getById(request.time());

        Reservation reservation = new Reservation(
            request.name(),
            request.date(),
            time
        );

        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.from(savedReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.getById(id);
        reservationRepository.deleteById(id);
    }
}
