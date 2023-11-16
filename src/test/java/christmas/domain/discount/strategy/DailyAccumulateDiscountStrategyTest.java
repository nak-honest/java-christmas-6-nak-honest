package christmas.domain.discount.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Money;
import christmas.domain.reservation.OrderMenus;
import christmas.domain.reservation.Reservation;
import christmas.domain.menu.Menu;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DailyAccumulateDiscountStrategyTest {
    static final int TEST_YEAR = 2023;
    static final Month TEST_MONTH = Month.DECEMBER;
    static final LocalDate BASE_DATE = LocalDate.of(TEST_YEAR, TEST_MONTH, 1);
    static final Money BASE_AMOUNT = Money.of(1_000);
    static final Money INCREASE_AMOUNT_PER_DAY = Money.of(100);

    static Stream<Arguments> provideDaysAndVariableDiscountAmount() {
        return Stream.of(
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 1), 1_000),
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 2), 1_100),
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 3), 1_200),
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 4), 1_300),
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 5), 1_400)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDaysAndVariableDiscountAmount")
    void 방문_날짜에_따른_일일_누적_할인_금액을_계산한다(LocalDate visitDate, int expectedDiscountAmount) {
        // given
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        DailyAccumulateDiscountStrategy dailyAccumulateDiscountStrategy =
                DailyAccumulateDiscountStrategy.of(BASE_DATE, BASE_AMOUNT, INCREASE_AMOUNT_PER_DAY);
        Money actualDiscountAmount = dailyAccumulateDiscountStrategy.calculateDiscountAmount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(expectedDiscountAmount));
    }

    static Stream<Arguments> provideDaysAndFixedDiscountAmount() {
        return Stream.of(
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 1), 1_000),
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 2), 1_000),
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 3), 1_000),
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 4), 1_000),
                Arguments.of(LocalDate.of(TEST_YEAR, TEST_MONTH, 5), 1_000)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDaysAndFixedDiscountAmount")
    void 방문_날짜와_상관없이_고정된_할인_금액을_계산한다(LocalDate visitDate, int expectedDiscountAmount) {
        // given
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        DailyAccumulateDiscountStrategy dailyAccumulateDiscountStrategy =
                DailyAccumulateDiscountStrategy.fixed(BASE_AMOUNT);
        Money actualDiscountAmount = dailyAccumulateDiscountStrategy.calculateDiscountAmount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(expectedDiscountAmount));
    }
}
