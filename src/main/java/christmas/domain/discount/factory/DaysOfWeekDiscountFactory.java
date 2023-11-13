package christmas.domain.discount.factory;

import christmas.domain.Money;
import christmas.domain.discount.Discount;
import christmas.domain.discount.rule.DaysOfWeekDiscountRule;
import christmas.domain.discount.rule.DiscountRule;
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
        DiscountRule discountRule = new DaysOfWeekDiscountRule(WEEKDAYS);

        return new Discount("평일 할인", discountStrategy, discountRule);
    }

    public static Discount createWeekendsDiscount() {
        DiscountStrategy discountStrategy = new MenuTypeDiscountStrategy(MenuType.MAIN, DISCOUNT_AMOUNT_PER_MENU);
        DiscountRule discountRule = new DaysOfWeekDiscountRule(WEEKENDS);

        return new Discount("주말 할인", discountStrategy, discountRule);
    }
}
