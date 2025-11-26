package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.TimeCreateRequest;
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

        return timeRepository.save(time);
    }

    public void deleteTime(int id) {
        timeRepository.deleteById(id);
    }
}
