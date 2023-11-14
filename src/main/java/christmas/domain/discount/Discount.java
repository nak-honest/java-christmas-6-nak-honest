package christmas.domain.discount;

import christmas.domain.EventName;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.rule.EventRule;
import christmas.domain.discount.strategy.DiscountStrategy;

public class Discount {
    private final EventName eventName;
    private final DiscountStrategy discountStrategy;
    private final EventRule discountRule;

    public Discount(EventName eventName, DiscountStrategy discountStrategy, EventRule discountRule) {
        this.eventName = eventName;
        this.discountStrategy = discountStrategy;
        this.discountRule = discountRule;
    }

    public Money discount(Reservation reservation) {
        if (!discountRule.isSatisfiedBy(reservation)) {
            return Money.zeroInstance();
        }

        return discountStrategy.calculateDiscountAmount(reservation);
    }

    public EventName getEventName() {
        return eventName;
    }
}
