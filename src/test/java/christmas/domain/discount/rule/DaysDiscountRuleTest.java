package christmas.domain.discount.rule;

import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.MainMenu;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DaysDiscountRuleTest {
    static final Set<LocalDate> DISCOUNT_DATES = IntStream.rangeClosed(1, 15)
            .mapToObj(day -> LocalDate.of(2023, Month.DECEMBER, day))
            .collect(Collectors.toSet());
    static DaysDiscountRule daysDiscountRule = new DaysDiscountRule(DISCOUNT_DATES);

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
    void 방문_날짜가_주어진_할인_날짜에_해당하는_경우_할인을_적용한다(int visitDay) {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, visitDay);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.BARBECUE_RIB, 1)));

        // when
        boolean isDiscount = daysDiscountRule.isSatisfiedBy(reservation);

        // then
        assertThat(isDiscount).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31})
    void 방문_날짜가_주어진_할인_날짜에_해당되지_않는_경우_할인을_적용하지_않는다(int visitDay) {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, visitDay);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.BARBECUE_RIB, 1)));

        // when
        boolean isDiscount = daysDiscountRule.isSatisfiedBy(reservation);

        // then
        assertThat(isDiscount).isFalse();
    }
}
