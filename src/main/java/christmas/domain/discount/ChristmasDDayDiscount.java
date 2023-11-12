package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;

import java.time.LocalDate;
import java.time.Month;

public class ChristmasDDayDiscount implements Discount {
    private static final LocalDate START_DATE = LocalDate.of(2023, Month.DECEMBER, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, Month.DECEMBER, 25);
    private static final Money BASE_DISCOUNT_AMOUNT = Money.of(1_000);
    private static final Money DISCOUNT_AMOUNT_PER_DAY = Money.of(100);

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (!reservation.isBetween(START_DATE, END_DATE)) {
            return Money.zeroInstance();
        }

        return calculateDiscountAmountByDay(reservation);
    }

    private Money calculateDiscountAmountByDay(Reservation reservation) {
        int dayDiff = reservation.daysFrom(START_DATE);

        return BASE_DISCOUNT_AMOUNT.add(DISCOUNT_AMOUNT_PER_DAY.multiply(dayDiff));
    }
}
