package roomescape.utils;

import roomescape.model.dto.ReservationResponseDto;
import roomescape.model.entity.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationConverter {
    public static ReservationResponseDto toDto(final Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                TimeConverter.toDto(reservation.getTime())
        );
    }

    public static List<ReservationResponseDto> toDto(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationConverter::toDto)
                .collect(Collectors.toList());
    }
}
