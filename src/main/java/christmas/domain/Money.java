package christmas.domain;

import java.util.Objects;

public class Money {
    private static final Money ZERO_AMOUNT = new Money(0);

    private final int amount;

    private Money(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 음수일 수 없습니다.");
        }
    }

    public static Money zeroInstance() {
        return ZERO_AMOUNT;
    }

    public static Money of(int amount) {
        if (amount == 0) {
            return ZERO_AMOUNT;
        }
        return new Money(amount);
    }

    public Money multiply(int multiplier) {
        return Money.of(amount * multiplier);
    }

    public Money add(Money money) {
        return Money.of(this.amount + money.amount);
    }

    public boolean isGreaterThanOrEqual(Money money) {
        return this.amount >= money.amount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Money money)) {
            return false;
        }

        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
