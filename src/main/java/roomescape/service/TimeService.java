package roomescape.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.exception.CannotDeleteTimeWithExistingReservationException;
import roomescape.exception.DuplicateTimeException;
import roomescape.service.dto.TimeResult;
import roomescape.service.dto.TimeSaveCommand;

import java.util.List;


@Service
public class TimeService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao, ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public TimeResult save(TimeSaveCommand timeSaveCommand) {
        try {
            Time saveTime = timeDao.save(timeSaveCommand.toEntity());
            return TimeResult.from(saveTime);
        } catch (
                DuplicateKeyException e) {
            throw new DuplicateTimeException("이미 등록된 시간입니다");
        }
    }

    public List<TimeResult> findAll() {
        List<Time> times = timeDao.findAll();
        return times.stream()
                .map(TimeResult::from)
                .toList();
    }

    public void delete(Long id) {
        if (reservationDao.existsByTimeId(id)) {
            throw new CannotDeleteTimeWithExistingReservationException("저장된 시간이 있어서 삭제할 수 없습니다");
        }
        timeDao.delete(id);
    }
}
