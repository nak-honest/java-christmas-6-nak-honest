package christmas.domain.discount;

import christmas.domain.EventName;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.rule.EventRule;

import java.util.*;

public class Discounts {
    private final List<Discount> discounts;
    private final EventRule commonRule;

    public Discounts(List<Discount> discounts, EventRule commonRule) {
        this.discounts = new ArrayList<>(discounts);
        this.commonRule = commonRule;
    }

    public DiscountResult discount(Reservation reservation) {
        Map<EventName, Money> discountResult = new EnumMap<>(EventName.class);

        if (!commonRule.isSatisfiedBy(reservation)) {
            return new DiscountResult(discountResult);
        }

        discounts.forEach(discount ->
                discountResult.put(discount.getEventName(), discount.discount(reservation)));

        return new DiscountResult(discountResult);
    }
}
