package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;

public class ChristmasDDayDiscount {
    private static final int START_DAY = 1;
    private static final int END_DAY = 25;
    private static final int BASE_DISCOUNT_AMOUNT = 1_000;
    private static final int DISCOUNT_AMOUNT_PER_DAY = 100;

    public Money calculateDiscountAmount(Reservation reservation) {
        return calculateDiscountAmountByDay(reservation.getVisitDay());
    }

    private Money calculateDiscountAmountByDay(int visitDay) {
        if (visitDay >= START_DAY && visitDay <= END_DAY) {
            return new Money(BASE_DISCOUNT_AMOUNT + (visitDay - START_DAY) * DISCOUNT_AMOUNT_PER_DAY);
        }

        return new Money(0);
    }
}
