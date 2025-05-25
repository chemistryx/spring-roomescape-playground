package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.TimeRequest;
import roomescape.controller.dto.TimeResponse;
import roomescape.service.TimeService;
import roomescape.service.dto.TimeResult;

import java.net.URI;
import java.util.List;

@RestController
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> save(@RequestBody TimeRequest timeRequest) {
        TimeResult result = timeService.save(timeRequest.toCommand());
        return ResponseEntity.created(URI.create("/times/" + result.id()))
                .body(TimeResponse.from(result));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> findAll() {
        List<TimeResult> all = timeService.findAll();
        List<TimeResponse> timeResponses = all.stream()
                .map(TimeResponse::from)
                .toList();
        return ResponseEntity.ok(timeResponses);
    }
}
