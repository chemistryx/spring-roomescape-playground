package roomescape.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/reservation")
    public String readPage(Model model) {
        return "reservation";
    }
}
