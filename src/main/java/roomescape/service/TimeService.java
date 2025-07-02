package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public TimeResponseDto saveTime(TimeRequestDto requestDto) {
        LocalTime time = requestDto.getTimeValue();
        if (timeDao.existsByTime(time)) {
            throw new IllegalArgumentException("이미 동일한 시각이 존재합니다.");
        }

        Time newTime = new Time(null, requestDto.getTimeValue());
        Time saved = timeDao.save(newTime);
        return new TimeResponseDto(saved.getId(), saved.getTimeValue());
    }

    public List<TimeResponseDto> findAllTimes() {
        return timeDao.findAll()
                .stream()
                .map(t -> new TimeResponseDto(t.getId(), t.getTimeValue()))
                .collect(Collectors.toList());
    }

    public void deleteTime(Long timeId) {
        if (reservationDao.existsByTimeId(timeId)) {
            throw new IllegalStateException("해당 시각에 예약이 존재하여 삭제할 수 없습니다.");
        }

        timeDao.deleteById(timeId);
    }
}
