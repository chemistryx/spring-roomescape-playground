package roomescape.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import roomescape.domain.Time;

public interface TimeRepository {

    Optional<Time> findById(Long id);

    List<Time> findAll();

    Time save (Time time);

    void deleteById(Long id);

    default Time getById(Long id) {
        return findById(id)
            .orElseThrow(NoSuchElementException::new);
    }
}
