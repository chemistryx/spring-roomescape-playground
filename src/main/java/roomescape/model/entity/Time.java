package roomescape.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

@RequiredArgsConstructor
@Builder
@Getter
public class Time {

    private final long id;
    private final LocalTime time;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Time time1)) return false;
        return Objects.equals(getTime(), time1.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime());
    }
}
