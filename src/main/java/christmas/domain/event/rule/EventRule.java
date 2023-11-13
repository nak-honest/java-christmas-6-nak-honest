package christmas.domain.event.rule;

import christmas.domain.Reservation;

public interface EventRule {
    boolean isSatisfiedBy(Reservation reservation);
}
