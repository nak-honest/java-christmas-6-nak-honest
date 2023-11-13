package christmas.domain.rule;

import christmas.domain.Reservation;

public interface EventRule {
    boolean isSatisfiedBy(Reservation reservation);
}
