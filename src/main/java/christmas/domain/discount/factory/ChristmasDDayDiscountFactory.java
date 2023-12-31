package christmas.domain.discount.factory;

import christmas.domain.Money;
import christmas.domain.discount.Discount;
import christmas.domain.EventName;
import christmas.domain.rule.DaysEventRule;
import christmas.domain.discount.strategy.DailyAccumulateDiscountStrategy;
import christmas.domain.discount.strategy.DiscountStrategy;
import christmas.domain.rule.EventRule;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChristmasDDayDiscountFactory {
    private static final LocalDate START_DATE = LocalDate.of(2023, Month.DECEMBER, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, Month.DECEMBER, 25);
    private static final Set<LocalDate> DISCOUNT_DAYS = IntStream.rangeClosed(1, 31)
            .mapToObj(day -> LocalDate.of(2023, Month.DECEMBER, day))
            .filter(date -> !date.isAfter(END_DATE) && !date.isBefore(START_DATE))
            .collect(Collectors.toSet());
    private static final Money BASE_DISCOUNT_AMOUNT = Money.of(1_000);
    private static final Money DISCOUNT_AMOUNT_PER_DAY = Money.of(100);

    private ChristmasDDayDiscountFactory() {}

    public static Discount create() {
        DiscountStrategy discountStrategy =
                DailyAccumulateDiscountStrategy.of(START_DATE, BASE_DISCOUNT_AMOUNT, DISCOUNT_AMOUNT_PER_DAY);
        EventRule discountRule = new DaysEventRule(DISCOUNT_DAYS);

        return new Discount(EventName.CHRISTMAS_D_DAY_DISCOUNT, discountStrategy, discountRule);
    }
}
