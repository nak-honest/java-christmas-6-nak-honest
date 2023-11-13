package christmas.domain.badge;

import christmas.domain.Money;

import java.util.Arrays;
import java.util.Comparator;

public enum EventBadge {
    NONE(0),
    STAR(5_000),
    TREE(10_000),
    SANTA(20_000);

    private final Money minBenefitAmount;

    EventBadge(int minBenefitAmount) {
        this.minBenefitAmount = Money.of(minBenefitAmount);
    }

    public static EventBadge of(Money totalPrice) {
        return Arrays.stream(values())
                .sorted(Comparator.comparing(EventBadge::getMinBenefitAmount).reversed())
                .filter(badge -> badge.isSatisfiedBy(totalPrice))
                .findFirst()
                .orElse(NONE);
    }

    private Money getMinBenefitAmount() {
        return minBenefitAmount;
    }

    private boolean isSatisfiedBy(Money totalPrice) {
        return totalPrice.isGreaterThanOrEqual(minBenefitAmount);
    }
}
