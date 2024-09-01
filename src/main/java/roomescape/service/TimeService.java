package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Time createTime(Time time) {
        if (!isValidTime(time)) {
            throw new IllegalArgumentException("Invalid time");
        }
        return timeRepository.save(time);
    }

    public List<Time> getAllTimes() {
        return timeRepository.findAll();
    }

    public boolean deleteTime(Long id) {
        int rowsAffected = timeRepository.deleteById(id);
        return rowsAffected > 0;
    }

    public boolean isValidTime(Time time) {
        return time.getTime() != null && !time.getTime().isEmpty();
    }
}