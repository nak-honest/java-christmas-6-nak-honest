package christmas.domain.discount.rule;

import christmas.domain.Reservation;

public interface DiscountRule {
    boolean isSatisfiedBy(Reservation reservation);
}
