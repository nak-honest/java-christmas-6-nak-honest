package christmas.domain.discount.factory;

import christmas.domain.Money;
import christmas.domain.discount.Discount;
import christmas.domain.EventName;
import christmas.domain.rule.DaysOfWeekEventRule;
import christmas.domain.rule.EventRule;
import christmas.domain.discount.strategy.DiscountStrategy;
import christmas.domain.discount.strategy.MenuTypeDiscountStrategy;
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

        return new Discount(EventName.WEEKDAY_DISCOUNT, discountStrategy, discountRule);
    }

    public static Discount createWeekendsDiscount() {
        DiscountStrategy discountStrategy = new MenuTypeDiscountStrategy(MenuType.MAIN, DISCOUNT_AMOUNT_PER_MENU);
        EventRule discountRule = new DaysOfWeekEventRule(WEEKENDS);

        return new Discount(EventName.WEEKEND_DISCOUNT, discountStrategy, discountRule);
    }
}
