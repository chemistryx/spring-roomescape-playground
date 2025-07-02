package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<TimeResponseDto> create(@Valid @RequestBody TimeRequestDto requestDto) {
        TimeResponseDto responseDto = timeService.saveTime(requestDto);
        URI location = URI.create("/times/" + responseDto.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<TimeResponseDto>> findAll() {
        return ResponseEntity.ok(timeService.findAllTimes());
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
