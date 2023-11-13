package christmas.domain.event;

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

    public Map<Discount, Money> discount(Reservation reservation) {
        if (!commonRule.isSatisfiedBy(reservation)) {
            return Collections.emptyMap();
        }

        return discounts.stream()
                .filter(discount -> !discount.discount(reservation).equals(Money.zeroInstance()))
                .collect(Collectors.toMap(Function.identity(), discount -> discount.discount(reservation)));
    }
}
