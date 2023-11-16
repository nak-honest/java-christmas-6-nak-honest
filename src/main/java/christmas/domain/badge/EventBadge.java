package christmas.domain.badge;

import christmas.domain.Money;
import java.util.Arrays;
import java.util.Comparator;

public enum EventBadge {
    NONE("없음", 0),
    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000);

    private final String name;
    private final Money minBenefitAmount;

    EventBadge(String name, int minBenefitAmount) {
        this.name = name;
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

    public String getName() {
        return name;
    }
}
