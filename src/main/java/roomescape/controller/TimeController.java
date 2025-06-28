package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.model.dto.TimeRequestDto;
import roomescape.model.dto.TimeResponseDto;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @PostMapping
    public ResponseEntity<TimeResponseDto> addTime(@RequestBody TimeRequestDto timeRequestDto) {
        TimeResponseDto responseDto = timeService.addReservationTime(timeRequestDto);
        URI location = URI.create("/times/" + responseDto.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponseDto>> getTimes() {
        List<TimeResponseDto> responseDtos = timeService.findAllTimes();
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTime(@PathVariable long timeId) {
        timeService.deleteTimes(timeId);
        return ResponseEntity.noContent().build();
    }
}
