package christmas.domain.discount.factory;

import christmas.domain.Money;
import christmas.domain.discount.Discount;
import christmas.domain.discount.Discounts;
import christmas.domain.rule.EventRule;
import christmas.domain.rule.TotalPriceEventRule;
import java.util.List;

public class ChristmasPromotionDiscountsFactory {
    private static final Discount christmasDDayDiscount = ChristmasDDayDiscountFactory.create();
    private static final Discount weekdayDiscount = DaysOfWeekDiscountFactory.createWeekdaysDiscount();
    private static final Discount weekendDiscount = DaysOfWeekDiscountFactory.createWeekendsDiscount();
    private static final Discount specialDiscount = SpecialDiscountFactory.create();
    private static final Money COMMON_MINIMUM_PRICE = Money.of(10_000);
    private static final EventRule COMMON_DISCOUNT_RULE = new TotalPriceEventRule(COMMON_MINIMUM_PRICE);

    private ChristmasPromotionDiscountsFactory() {}

    public static Discounts create() {
        return new Discounts(
                List.of(christmasDDayDiscount, weekdayDiscount, weekendDiscount, specialDiscount),
                COMMON_DISCOUNT_RULE);
    }
}
