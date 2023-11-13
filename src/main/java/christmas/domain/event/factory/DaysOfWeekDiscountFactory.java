package christmas.domain.event.factory;

import christmas.domain.Money;
import christmas.domain.event.Discount;
import christmas.domain.event.DiscountType;
import christmas.domain.event.rule.DaysOfWeekEventRule;
import christmas.domain.event.rule.EventRule;
import christmas.domain.event.strategy.DiscountStrategy;
import christmas.domain.event.strategy.MenuTypeDiscountStrategy;
import christmas.domain.menu.MenuType;

import java.time.DayOfWeek;
import java.util.Set;

public class DaysOfWeekDiscountFactory {
    private static final Money DISCOUNT_AMOUNT_PER_MENU = Money.of(2_023);
    private static final Set<DayOfWeek> WEEKDAYS =
            Set.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);
    private static final Set<DayOfWeek> WEEKENDS =
            Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

    private DaysOfWeekDiscountFactory() {}

    public static Discount createWeekdaysDiscount() {
        DiscountStrategy discountStrategy = new MenuTypeDiscountStrategy(MenuType.DESSERT, DISCOUNT_AMOUNT_PER_MENU);
        EventRule discountRule = new DaysOfWeekEventRule(WEEKDAYS);

        return new Discount(DiscountType.WEEKDAY, discountStrategy, discountRule);
    }

    public static Discount createWeekendsDiscount() {
        DiscountStrategy discountStrategy = new MenuTypeDiscountStrategy(MenuType.MAIN, DISCOUNT_AMOUNT_PER_MENU);
        EventRule discountRule = new DaysOfWeekEventRule(WEEKENDS);

        return new Discount(DiscountType.WEEKEND, discountStrategy, discountRule);
    }
}
