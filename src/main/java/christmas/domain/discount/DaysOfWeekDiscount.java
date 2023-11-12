package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.menu.MenuType;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DaysOfWeekDiscount implements Discount {
    private static final Money DISCOUNT_AMOUNT_PER_MENU = Money.of(2_023);
    private static final Set<DayOfWeek> WEEKDAYS =
            Set.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);
    private static final Set<DayOfWeek> WEEKENDS =
            Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

    private final Set<DayOfWeek> discountDaysOfWeek;
    private final Money discountAmountPerMenu;
    private final MenuType discountMenuType;

    public DaysOfWeekDiscount(Set<DayOfWeek> discountDaysOfWeek, Money discountAmountPerMenu, MenuType discountMenuType) {
        this.discountDaysOfWeek = new HashSet<>(discountDaysOfWeek);
        this.discountAmountPerMenu = discountAmountPerMenu;
        this.discountMenuType = discountMenuType;
    }

    public static DaysOfWeekDiscount weekdaysDiscount() {
        return new DaysOfWeekDiscount(WEEKDAYS, DISCOUNT_AMOUNT_PER_MENU, MenuType.DESSERT);
    }

    public static DaysOfWeekDiscount weekendsDiscount() {
        return new DaysOfWeekDiscount(WEEKENDS, DISCOUNT_AMOUNT_PER_MENU, MenuType.MAIN);
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (!reservation.isDayOfWeekIn(discountDaysOfWeek)) {
            return Money.zeroInstance();
        }

        return calculateDiscountAmountByMenu(reservation);
    }

    private Money calculateDiscountAmountByMenu(Reservation reservation) {
        int discountMenuCount = reservation.countMenuByType(discountMenuType);
        return discountAmountPerMenu.multiply(discountMenuCount);
    }
}
