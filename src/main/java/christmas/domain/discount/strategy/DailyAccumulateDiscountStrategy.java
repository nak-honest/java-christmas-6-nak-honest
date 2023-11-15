package christmas.domain.discount.strategy;

import christmas.domain.Money;
import christmas.domain.reservation.Reservation;
import java.time.LocalDate;

public class DailyAccumulateDiscountStrategy implements DiscountStrategy {
    private final LocalDate baseDate;
    private final Money baseAmount;
    private final Money increaseAmountPerDay;

    private DailyAccumulateDiscountStrategy(LocalDate baseDate, Money baseAmount, Money increaseAmountPerDay) {
        this.baseDate = baseDate;
        this.baseAmount = baseAmount;
        this.increaseAmountPerDay = increaseAmountPerDay;
    }

    public static DailyAccumulateDiscountStrategy of(LocalDate baseDate, Money baseAmount, Money increaseAmountPerDay) {
        return new DailyAccumulateDiscountStrategy(baseDate, baseAmount, increaseAmountPerDay);
    }

    public static DailyAccumulateDiscountStrategy fixed(Money baseAmount) {
        return new DailyAccumulateDiscountStrategy(LocalDate.now(), baseAmount, Money.zeroInstance());
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        int daysFromBaseDate = reservation.daysFrom(baseDate);

        return baseAmount.add(increaseAmountPerDay.multiply(daysFromBaseDate));
    }
}
