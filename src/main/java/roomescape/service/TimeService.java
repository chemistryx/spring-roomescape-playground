package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.dto.TimeRequest;
import roomescape.domain.dto.TimeResponse;
import roomescape.domain.entity.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponse> findAll() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::new)
                .toList();
    }

    @Transactional
    public TimeResponse save(TimeRequest request) {
        Long id = timeRepository.save(request.time());
        return new TimeResponse(new Time(id, request.time()));
    }

    public void deleteById(Long id) {
        timeRepository.deleteById(id);
    }
}
