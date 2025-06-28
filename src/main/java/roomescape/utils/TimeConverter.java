package roomescape.utils;

import roomescape.model.dto.TimeResponseDto;
import roomescape.model.entity.Time;

import java.util.List;
import java.util.stream.Collectors;

public class TimeConverter {
    public static TimeResponseDto toDto(final Time time) {
        return new TimeResponseDto(
                time.getId(),
                time.getTime()
        );
    }

    public static List<TimeResponseDto> toDto(final List<Time> times) {
        return times.stream().map(TimeConverter::toDto).collect(Collectors.toList());
    }
}
