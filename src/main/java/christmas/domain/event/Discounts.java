package christmas.domain.event;

import christmas.domain.DiscountResult;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.event.rule.EventRule;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Discounts {
    private final List<Discount> discounts;
    private final EventRule commonRule;

    public Discounts(List<Discount> discounts, EventRule commonRule) {
        this.discounts = new ArrayList<>(discounts);
        this.commonRule = commonRule;
    }

    public DiscountResult discount(Reservation reservation) {
        Map<DiscountType, Money> discountAmounts = new EnumMap<>(DiscountType.class);

        if (!commonRule.isSatisfiedBy(reservation)) {
            return new DiscountResult(discountAmounts);
        }

        discounts.forEach(discount ->
                discountAmounts.put(discount.getDiscountType(), discount.discount(reservation)));

        return new DiscountResult(discountAmounts);
    }
}
