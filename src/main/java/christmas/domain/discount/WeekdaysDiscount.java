package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.menu.MenuType;

import java.time.DayOfWeek;
import java.util.List;

public class WeekdaysDiscount implements Discount {
    private static final List<DayOfWeek> DISCOUNT_DAYS_OF_WEEK =
            List.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);
    private static final int DISCOUNT_AMOUNT_PER_MENU = 2_023;
    private static final MenuType DISCOUNT_MENU_TYPE = MenuType.DESSERT;

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (!isDiscountDayOfWeek(reservation)) {
            return Money.zeroInstance();
        }

        return calculateDiscountAmountByMenu(reservation);
    }

    private boolean isDiscountDayOfWeek(Reservation reservation) {
        return DISCOUNT_DAYS_OF_WEEK.stream()
                .anyMatch(reservation::isDayOfWeek);
    }

    private Money calculateDiscountAmountByMenu(Reservation reservation) {
        int discountMenuCount = reservation.countMenuByType(DISCOUNT_MENU_TYPE);
        return new Money(discountMenuCount * DISCOUNT_AMOUNT_PER_MENU);
    }
}
