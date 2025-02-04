package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        initializeTestData();
    }

    private void initializeTestData() {
        reservations.add(new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)));
        reservations.add(new Reservation(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)));
        reservations.add(new Reservation(3L, "브라운", LocalDate.of(2023, 1, 3), LocalTime.of(12, 0)));
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }
}
