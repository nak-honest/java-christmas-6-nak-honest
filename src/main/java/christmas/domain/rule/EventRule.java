package christmas.domain.rule;

import christmas.domain.reservation.Reservation;

public interface EventRule {
    boolean isSatisfiedBy(Reservation reservation);
}
