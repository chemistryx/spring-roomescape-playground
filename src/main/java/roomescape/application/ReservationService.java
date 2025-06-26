package roomescape.application;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.CreateReservationRequest;
import roomescape.application.dto.DeleteReservationRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.model.Reservation;
import roomescape.domain.repository.ReservationRepository;
import roomescape.infra.repository.InMemoryReservationRepository;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public ReservationService(final InMemoryReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  public List<ReservationResponse> findAll() {
    return reservationRepository.findAll().stream().map(ReservationResponse::from).toList();
  }

  public Reservation save(final CreateReservationRequest createReservationRequest) {
    final Long id = reservationRepository.nextId();
    final Reservation reservation = createReservationRequest.toReservation(id);

    return reservationRepository.save(reservation);
  }

  public void deleteById(final DeleteReservationRequest deleteReservationRequest) {
    final Reservation reservation = reservationRepository.findById(deleteReservationRequest.reservationId());

    reservationRepository.deleteById(reservation.getId());
  }
}
