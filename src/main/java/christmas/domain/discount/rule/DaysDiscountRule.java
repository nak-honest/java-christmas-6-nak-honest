package christmas.domain.discount.rule;

import christmas.domain.Reservation;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DaysDiscountRule implements DiscountRule {
    private final Set<LocalDate> discountDays;

    public DaysDiscountRule(Set<LocalDate> discountDays) {
        this.discountDays = new HashSet<>(discountDays);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        return reservation.isDayIn(discountDays);
    }
}
