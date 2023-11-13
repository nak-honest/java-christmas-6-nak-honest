package christmas.domain.discount.factory;

import christmas.domain.Money;
import christmas.domain.discount.Discount;
import christmas.domain.discount.rule.DaysDiscountRule;
import christmas.domain.discount.rule.DiscountRule;
import christmas.domain.discount.strategy.DailyAccumulateDiscountStrategy;
import christmas.domain.discount.strategy.DiscountStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SpecialDiscountFactory {
    private static final String NAME = "특별 할인";
    private static final Money DISCOUNT_AMOUNT = Money.of(1_000);
    private static final Set<LocalDate> DISCOUNT_DAYS = IntStream.rangeClosed(1, 31)
            .mapToObj(day -> LocalDate.of(2023, Month.DECEMBER, day))
            .filter(date -> date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfMonth() == 25)
            .collect(Collectors.toSet());

    private SpecialDiscountFactory() {}

    public static Discount create() {
        DiscountStrategy discountStrategy = DailyAccumulateDiscountStrategy.fixed(DISCOUNT_AMOUNT);
        DiscountRule discountRule = new DaysDiscountRule(DISCOUNT_DAYS);

        return new Discount(NAME, discountStrategy, discountRule);
    }
}
