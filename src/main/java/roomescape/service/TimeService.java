package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.model.dto.TimeRequestDto;
import roomescape.model.dto.TimeResponseDto;
import roomescape.model.entity.Time;
import roomescape.repository.TimeRepository;
import roomescape.utils.TimeConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeResponseDto addReservationTime(TimeRequestDto timeRequestDto) {
        Time timeWithoutId = Time.builder()
                .time(timeRequestDto.getTime())
                .build();

        List<Time> alreadyCreatedTimes = timeRepository.findAll();

        if (alreadyCreatedTimes.contains(timeWithoutId)) {
            throw new BadRequestException("이미 추가된 시간입니다.");
        }
        return TimeConverter.toDto(timeRepository.save(timeWithoutId));
    }

    public List<TimeResponseDto> findAllTimes() {
        return TimeConverter.toDto(timeRepository.findAll());
    }

    public void deleteTimes(long id) {
        int result = timeRepository.deleteById(id);
        if (result == 0) {
            throw new BadRequestException("id 값과 일치하는 등록된 시간이 일치하지 않습니다.");
        }
    }

}
