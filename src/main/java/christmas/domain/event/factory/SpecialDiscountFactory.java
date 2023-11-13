package christmas.domain.event.factory;

import christmas.domain.Money;
import christmas.domain.event.Discount;
import christmas.domain.event.rule.DaysEventRule;
import christmas.domain.event.rule.EventRule;
import christmas.domain.event.strategy.DailyAccumulateDiscountStrategy;
import christmas.domain.event.strategy.DiscountStrategy;

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
        EventRule discountRule = new DaysEventRule(DISCOUNT_DAYS);

        return new Discount(NAME, discountStrategy, discountRule);
    }
}
