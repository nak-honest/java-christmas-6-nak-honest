package christmas.domain;

import christmas.domain.event.DiscountType;

import java.util.EnumMap;
import java.util.Map;

public class DiscountResult {
    private final Map<DiscountType, Money> discountAmounts;

    public DiscountResult(Map<DiscountType, Money> discountAmounts) {
        this.discountAmounts = new EnumMap<>(discountAmounts);
    }

    public Money getTotalDiscountAmounts() {
        return discountAmounts.values().stream()
                .reduce(Money.zeroInstance(), Money::add);
    }
}
