package roomescape.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.Time;
import roomescape.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void testFindAll() throws Exception {
        ReservationResponse response1 = new ReservationResponse(new Reservation(1L, "seongmin", LocalDate.now().plusDays(1), new Time(1L, LocalTime.of(10, 0))));
        ReservationResponse response2 = new ReservationResponse(new Reservation(2L, "theo", LocalDate.now().plusDays(2), new Time(2L, LocalTime.of(11, 0))));

        when(reservationService.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("seongmin"))
                .andExpect(jsonPath("$[0].date").value(LocalDate.now().plusDays(1).toString()))
                .andExpect(jsonPath("$[0].time.id").value(1L))
                .andExpect(jsonPath("$[0].time.time").value("10:00:00"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("theo"))
                .andExpect(jsonPath("$[1].date").value(LocalDate.now().plusDays(2).toString()))
                .andExpect(jsonPath("$[1].time.id").value(2L))
                .andExpect(jsonPath("$[1].time.time").value("11:00:00"));
    }

    @Test
    void testSave() throws Exception {
        ReservationRequest request = new ReservationRequest(LocalDate.now().plusDays(1), "seongmin", 1L);
        Reservation reservation = new Reservation(1L, "seongmin", request.date(), new Time(1L, LocalTime.of(10, 0)));
        ReservationResponse response = new ReservationResponse(reservation);

        when(reservationService.save(any(ReservationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "seongmin",
                            "date": "%s",
                            "time": 1
                        }
                        """.formatted(request.date())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reservations/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("seongmin"))
                .andExpect(jsonPath("$.time.id").value(1L))
                .andExpect(jsonPath("$.time.time").value("10:00:00"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isNoContent());
    }
}
