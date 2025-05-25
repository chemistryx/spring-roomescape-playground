package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.NotFoundTimeException;
import roomescape.service.dto.ReservationSaveCommand;
import roomescape.service.dto.ReservationResult;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public ReservationResult save(ReservationSaveCommand command) {
        Time time = timeDao.findById(command.timeId())
                .orElseThrow(() -> new NotFoundTimeException("존재하지 않는 시간입니다. id = " + command.timeId()));
        Reservation reservation = command.toEntity(time);
        Reservation savedReservation = reservationDao.save(reservation);
        return ReservationResult.from(savedReservation);
    }

    public List<ReservationResult> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResult::from)
                .toList();
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
