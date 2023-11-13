package christmas.domain.event;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.event.rule.EventRule;
import christmas.domain.event.strategy.DiscountStrategy;

public class Discount {
    private final String name;
    private final DiscountStrategy discountStrategy;
    private final EventRule discountRule;

    public Discount(String name, DiscountStrategy discountStrategy, EventRule discountRule) {
        this.name = name;
        this.discountStrategy = discountStrategy;
        this.discountRule = discountRule;
    }

    public Money discount(Reservation reservation) {
        if (!discountRule.isSatisfiedBy(reservation)) {
            return Money.zeroInstance();
        }

        return discountStrategy.calculateDiscountAmount(reservation);
    }
}
