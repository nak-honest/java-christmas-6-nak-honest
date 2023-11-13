package christmas.domain.discount.rule;

import christmas.domain.Reservation;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class DaysOfWeekDiscountRule implements DiscountRule {
    private final Set<DayOfWeek> discountDaysOfWeek;

    public DaysOfWeekDiscountRule(Set<DayOfWeek> discountDaysOfWeek) {
        this.discountDaysOfWeek = new HashSet<>(discountDaysOfWeek);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        return reservation.isDayOfWeekIn(discountDaysOfWeek);
    }
}
