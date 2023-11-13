package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.discount.rule.DiscountRule;
import christmas.domain.discount.strategy.DiscountStrategy;

public class Discount {
    private final String name;
    private final DiscountStrategy discountStrategy;
    private final DiscountRule discountRule;

    public Discount(String name, DiscountStrategy discountStrategy, DiscountRule discountRule) {
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
