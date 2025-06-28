package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.model.dto.ReservationRequestDto;
import roomescape.model.dto.ReservationResponseDto;
import roomescape.model.entity.Reservation;
import roomescape.model.entity.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;
import roomescape.utils.ReservationConverter;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(final ReservationRepository reservationRepository, final TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ReservationConverter.toDto(reservations);
    }

    public ReservationResponseDto createReservation(final ReservationRequestDto requestDto) {

        Time reservationTime = timeRepository.findById(requestDto.getTimeId());

        Reservation reservationWithoutId = Reservation.builder()
                .date(requestDto.getDate())
                .name(requestDto.getName())
                .time(reservationTime)
                .build();

        if (isReservationDuplicated(reservationWithoutId)) {
            throw new BadRequestException("이미 해당 시간에 예약이 존재합니다.");
        }

        Reservation createdReservation = reservationRepository.save(reservationWithoutId);
        return ReservationConverter.toDto(createdReservation);
    }

    private boolean isReservationDuplicated(Reservation newReservation) {

        List<Reservation> alreadyCreatedReservations = reservationRepository.findAll();

        boolean isDuplicated = alreadyCreatedReservations.stream()
                .anyMatch(reservation ->
                        reservation.getDate().equals(newReservation.getDate()) &&
                                reservation.getTime().equals(newReservation.getTime())
                );

        return isDuplicated;
    }

    public void deleteReservation(final Long reservationId) {
        int result = reservationRepository.deleteById(reservationId);
        if (result == 0) {
            throw new BadRequestException("id값에 해당하는 예약이 존재하지 않습니다.");
        }
    }
}
