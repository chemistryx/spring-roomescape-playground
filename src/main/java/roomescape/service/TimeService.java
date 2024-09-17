package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roomescape.controller.dto.request.TimeCreateRequest;
import roomescape.controller.dto.response.TimeResponse;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

@Service
@Transactional(readOnly = true)
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> getTimes() {
        List<Time> times = timeRepository.findAll();

        return times.stream()
            .map(TimeResponse::from)
            .toList();
    }

    @Transactional
    public TimeResponse createTime(
        TimeCreateRequest request
    ) {
        Time time = new Time(request.time());
        Time savedTime = timeRepository.save(time);

        return TimeResponse.from(savedTime);
    }

    @Transactional
    public void deleteTime(Long id) {
        timeRepository.getById(id);
        timeRepository.deleteById(id);
    }
}
