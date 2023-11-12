package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SpecialDiscount implements Discount {
    private static final Money DISCOUNT_AMOUNT = Money.of(1_000);
    private static final Set<LocalDate> DISCOUNT_DAYS = IntStream.rangeClosed(1, 31)
            .mapToObj(day -> LocalDate.of(2023, Month.DECEMBER, day))
            .filter(date -> date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfMonth() == 25)
            .collect(Collectors.toSet());

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (!reservation.isDayIn(DISCOUNT_DAYS)) {
            return Money.zeroInstance();
        }

        return DISCOUNT_AMOUNT;
    }
}
