package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.menu.MenuType;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DaysOfWeekDiscount implements Discount {
    private static final List<DayOfWeek> WEEKDAYS =
            List.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);
    private static final int DISCOUNT_AMOUNT_PER_MENU = 2_023;

    private final List<DayOfWeek> discountDaysOfWeek;
    private final int discountAmountPerMenu;
    private final MenuType discountMenuType;

    public DaysOfWeekDiscount(List<DayOfWeek> discountDaysOfWeek, int discountAmountPerMenu, MenuType discountMenuType) {
        this.discountDaysOfWeek = new ArrayList<>(discountDaysOfWeek);
        this.discountAmountPerMenu = discountAmountPerMenu;
        this.discountMenuType = discountMenuType;
    }

    public static DaysOfWeekDiscount weekdaysDiscount() {
        return new DaysOfWeekDiscount(WEEKDAYS, DISCOUNT_AMOUNT_PER_MENU, MenuType.DESSERT);
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (!isDiscountDayOfWeek(reservation)) {
            return Money.zeroInstance();
        }

        return calculateDiscountAmountByMenu(reservation);
    }

    private boolean isDiscountDayOfWeek(Reservation reservation) {
        return discountDaysOfWeek.stream()
                .anyMatch(reservation::isDayOfWeek);
    }

    private Money calculateDiscountAmountByMenu(Reservation reservation) {
        int discountMenuCount = reservation.countMenuByType(discountMenuType);
        return new Money(discountMenuCount * discountAmountPerMenu);
    }
}
