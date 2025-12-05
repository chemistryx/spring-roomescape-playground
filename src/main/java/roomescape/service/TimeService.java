package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.dto.TimeCreateRequest;
import roomescape.exception.DuplicateTimeException;
import roomescape.exception.TimeInUseException;
import roomescape.exception.TimeNotFoundException;
import roomescape.model.Time;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> getTimes() {
        return timeRepository.findAll();
    }

    public Time getById(int id) {
        return timeRepository.findById(id).orElseThrow(() -> new TimeNotFoundException("올바르지 않은 시간 ID 입니다."));
    }

    public Time createTime(TimeCreateRequest request) {
        LocalTime parsedTime = LocalTime.parse(request.time());

        Time time = Time.create(parsedTime);

        try {
            return timeRepository.save(time);
        } catch (DuplicateKeyException e) {
            throw new DuplicateTimeException("이미 존재하는 시간입니다.");
        }
    }

    public void deleteTime(int id) {
        try {
            int result = timeRepository.deleteById(id);

            if (result == 0) throw new TimeNotFoundException("시간이 존재하지 않습니다.");
        } catch (DataIntegrityViolationException e) {
            throw new TimeInUseException("예약에 사용중인 시간은 삭제할 수 없습니다.");
        }
    }
}
