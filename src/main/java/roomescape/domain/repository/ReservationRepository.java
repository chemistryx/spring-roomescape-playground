package roomescape.domain.repository;

import java.util.List;
import roomescape.domain.model.Reservation;

public interface ReservationRepository {

  long nextId();

  Reservation findById(Long id);

  List<Reservation> findAll();

  Reservation save(Reservation reservation);

  void deleteById(Long id);
}
