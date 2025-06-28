package roomescape.model.entity;

import lombok.Builder;
import lombok.Getter;
import roomescape.exception.BadRequestException;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Builder
public class Reservation {

    private final long id;

    private final String name;

    private final LocalDate date;

    private final Time time;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void validateArgumentNotEmpty(final String name, final LocalDate date, final Time time) {
        if (name.isBlank()) {
            throw new BadRequestException("이름 필드는 비어있을 수 없습니다.");
        }
        if (date == null || time == null) {
            throw new BadRequestException("날짜, 시간 필드는 비어있을 수 없습니다.");
        }
    }
}
