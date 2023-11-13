package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.rule.EventRule;
import christmas.domain.discount.strategy.DiscountStrategy;

public class Discount {
    private final DiscountType discountType;
    private final DiscountStrategy discountStrategy;
    private final EventRule discountRule;

    public Discount(DiscountType discountType, DiscountStrategy discountStrategy, EventRule discountRule) {
        this.discountType = discountType;
        this.discountStrategy = discountStrategy;
        this.discountRule = discountRule;
    }

    public Money discount(Reservation reservation) {
        if (!discountRule.isSatisfiedBy(reservation)) {
            return Money.zeroInstance();
        }

        return discountStrategy.calculateDiscountAmount(reservation);
    }

    public DiscountType getDiscountType() {
        return discountType;
    }
}
