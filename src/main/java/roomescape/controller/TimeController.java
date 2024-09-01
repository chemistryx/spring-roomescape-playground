package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
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
    public String showTimePage() {
        return "time";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        Time newTime = timeService.createTime(time);

        return ResponseEntity.created(URI.create("/times/" + newTime.getId()))
                .body(newTime);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times = timeService.getAllTimes();
        return ResponseEntity.ok().body(times);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteTime(@PathVariable Long id) {
        boolean isDeleted = timeService.deleteTime(id);
        if (!isDeleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
