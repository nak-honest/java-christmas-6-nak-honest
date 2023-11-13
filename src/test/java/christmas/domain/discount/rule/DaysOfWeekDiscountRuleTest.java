package christmas.domain.discount.rule;

import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.MainMenu;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DaysOfWeekDiscountRuleTest {
    static final int TEST_YEAR = 2023;
    static final Month TEST_MONTH = Month.DECEMBER;
    static final Set<DayOfWeek> DISCOUNT_DAYS_OF_WEEK = Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    static DaysOfWeekDiscountRule daysOfWeekDiscountRule = new DaysOfWeekDiscountRule(DISCOUNT_DAYS_OF_WEEK);

    static Set<LocalDate> provideDiscountDates() {
        return IntStream.rangeClosed(1, TEST_MONTH.length(Year.isLeap(TEST_YEAR)))
                .mapToObj(day -> LocalDate.of(TEST_YEAR, TEST_MONTH, day))
                .filter(date -> DISCOUNT_DAYS_OF_WEEK.contains(date.getDayOfWeek()))
                .collect(Collectors.toSet());
    }

    @ParameterizedTest
    @MethodSource("provideDiscountDates")
    void 방문_날짜가_주어진_요일에_해당하는_경우_할인을_적용한다(LocalDate visitDate) {
        // given
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.BARBECUE_RIB, 1)));

        // when
        boolean isDiscount = daysOfWeekDiscountRule.isSatisfiedBy(reservation);

        // then
        assertThat(isDiscount).isTrue();
    }

    static Set<LocalDate> provideNotDiscountDates() {
        return IntStream.rangeClosed(1, TEST_MONTH.length(Year.isLeap(TEST_YEAR)))
                .mapToObj(day -> LocalDate.of(TEST_YEAR, TEST_MONTH, day))
                .filter(date -> !DISCOUNT_DAYS_OF_WEEK.contains(date.getDayOfWeek()))
                .collect(Collectors.toSet());
    }

    @ParameterizedTest
    @MethodSource("provideNotDiscountDates")
    void 방문_날짜가_주어진_요일에_해당하지_않는_경우_할인을_적용하지_않는다(LocalDate visitDate) {
        // given
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.BARBECUE_RIB, 1)));

        // when
        boolean isDiscount = daysOfWeekDiscountRule.isSatisfiedBy(reservation);

        // then
        assertThat(isDiscount).isFalse();
    }
}
