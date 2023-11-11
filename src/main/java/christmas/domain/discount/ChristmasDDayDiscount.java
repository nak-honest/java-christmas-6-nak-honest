package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;

import java.time.LocalDate;
import java.time.Month;

public class ChristmasDDayDiscount implements Discount {
    private static final LocalDate START_DATE = LocalDate.of(2023, Month.DECEMBER, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, Month.DECEMBER, 25);
    private static final int BASE_DISCOUNT_AMOUNT = 1_000;
    private static final int DISCOUNT_AMOUNT_PER_DAY = 100;

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (!reservation.isBetween(START_DATE, END_DATE)) {
            return Money.zeroInstance();
        }

        return calculateDiscountAmountByDay(reservation);
    }

    private Money calculateDiscountAmountByDay(Reservation reservation) {
        int visitDay = reservation.getVisitDay();
        int dayDiff = visitDay - START_DATE.getDayOfMonth();

        return new Money(BASE_DISCOUNT_AMOUNT + dayDiff * DISCOUNT_AMOUNT_PER_DAY);
    }
}
