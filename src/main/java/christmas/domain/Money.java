package christmas.domain;

public record Money(int amount) {
    private static final Money ZERO_AMOUNT = new Money(0);

    public Money {
        if (amount < 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 음수일 수 없습니다.");
        }
    }

    public static Money zeroInstance() {
        return ZERO_AMOUNT;
    }
}
