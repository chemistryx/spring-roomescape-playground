package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        if (!isValidReservation(reservation)) {
            throw new NotFoundReservationException("Invalid reservation details or time_id not found");
        }
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public boolean deleteReservation(Long id) {
        int rowsAffected = reservationRepository.deleteById(id);
        return rowsAffected > 0;
    }

    public boolean isValidReservation(Reservation reservation) {
        return reservation.getName() != null && !reservation.getName().isEmpty() &&
                reservation.getDate() != null && !reservation.getDate().isEmpty() &&
                reservation.getTime() != null && reservation.getTime().getId() != null;
    }
}