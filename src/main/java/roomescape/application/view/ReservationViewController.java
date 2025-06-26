package roomescape.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationViewController {

  private static final String VIEW_RESERVATION = "reservation";

  @GetMapping("/reservation")
  public String reservation() {
    return VIEW_RESERVATION;
  }
}
