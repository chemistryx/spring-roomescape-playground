package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.Time;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @Transactional
    public ReservationResponse save(ReservationRequest request) {
        Time time = timeRepository.findById(request.timeId());
        Long id = reservationRepository.save(request.name(), request.date(), time.getId());
        Reservation reservation = new Reservation(id, request.name(), request.date(), time);
        return new ReservationResponse(reservation);
    }

    public void deleteById(Long id) {
        if (id == null || !reservationRepository.existsById(id)) {
            throw new InvalidReservationRequestException("이미 삭제된 예약입니다.");
        }
        reservationRepository.deleteById(id);
    }
}
