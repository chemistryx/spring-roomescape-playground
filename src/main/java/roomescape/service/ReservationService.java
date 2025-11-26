package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationCreateRequest;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeService timeService;

    public ReservationService(ReservationRepository reservationRepository, TimeService timeService) {
        this.reservationRepository = reservationRepository;
        this.timeService = timeService;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        Time time = timeService.getById(Integer.parseInt(request.time())); // DTO 단에서 검증 완료

        Reservation reservation = Reservation.create(request.name(), date, time);

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }
}
