package roomescape.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

  private final Long id;
  private final String name;
  private final LocalDate date;
  private final LocalTime time;

  public Reservation(final Long reservationId, final String userName, final LocalDate date,
      final LocalTime time) {
    this.id = reservationId;
    this.name = userName;
    this.date = date;
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getTime() {
    return time;
  }
}
