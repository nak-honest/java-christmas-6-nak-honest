package christmas.domain.rule;

import christmas.domain.Money;
import christmas.domain.Reservation;

public class TotalPriceEventRule implements EventRule {
    private final Money minPrice;

    public TotalPriceEventRule(Money minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        return reservation.isTotalPriceAtLeast(minPrice);
    }
}
