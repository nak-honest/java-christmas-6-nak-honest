package christmas.domain.discount;

import christmas.domain.EventName;
import christmas.domain.Money;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class DiscountResult {
    private final Map<EventName, Money> discountResult;

    public DiscountResult(Map<EventName, Money> discountResult) {
        this.discountResult = new EnumMap<>(discountResult);
    }

    public Money getTotalDiscountAmounts() {
        return discountResult.values().stream()
                .reduce(Money.zeroInstance(), Money::add);
    }

    public Map<EventName, Money> getDiscountResult() {
        return Collections.unmodifiableMap(discountResult);
    }
}
