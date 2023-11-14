package christmas.domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.Menu;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class DaysOfWeekEventRuleTest {
    static final int TEST_YEAR = 2023;
    static final Month TEST_MONTH = Month.DECEMBER;
    static final Set<DayOfWeek> EVENT_DAYS_OF_WEEK = Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    static DaysOfWeekEventRule daysOfWeekEventRule = new DaysOfWeekEventRule(EVENT_DAYS_OF_WEEK);

    static Set<LocalDate> provideEventDates() {
        return IntStream.rangeClosed(1, TEST_MONTH.length(Year.isLeap(TEST_YEAR)))
                .mapToObj(day -> LocalDate.of(TEST_YEAR, TEST_MONTH, day))
                .filter(date -> EVENT_DAYS_OF_WEEK.contains(date.getDayOfWeek()))
                .collect(Collectors.toSet());
    }

    @ParameterizedTest
    @MethodSource("provideEventDates")
    void 방문_날짜가_이벤트_요일에_해당하는_경우_이벤트를_적용한다(LocalDate visitDate) {
        // given
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        boolean isEvent = daysOfWeekEventRule.isSatisfiedBy(reservation);

        // then
        assertThat(isEvent).isTrue();
    }

    static Set<LocalDate> provideNoEventDates() {
        return IntStream.rangeClosed(1, TEST_MONTH.length(Year.isLeap(TEST_YEAR)))
                .mapToObj(day -> LocalDate.of(TEST_YEAR, TEST_MONTH, day))
                .filter(date -> !EVENT_DAYS_OF_WEEK.contains(date.getDayOfWeek()))
                .collect(Collectors.toSet());
    }

    @ParameterizedTest
    @MethodSource("provideNoEventDates")
    void 방문_날짜가_이벤트_요일에_해당하지_않는_경우_이벤트를_적용하지_않는다(LocalDate visitDate) {
        // given
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        boolean isEvent = daysOfWeekEventRule.isSatisfiedBy(reservation);

        // then
        assertThat(isEvent).isFalse();
    }
}
