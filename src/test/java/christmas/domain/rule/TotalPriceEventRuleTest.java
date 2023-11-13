package christmas.domain.rule;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.Menu;
import christmas.domain.rule.TotalPriceEventRule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TotalPriceEventRuleTest {
    static final TotalPriceEventRule totalPriceEventRule = new TotalPriceEventRule(Money.of(10_000));

    static Stream<Arguments> provideMenusAtLeastMinPrice() {
        return Stream.of(
                Arguments.of(Map.of(Menu.ICE_CREAM, 2)), // 10,000원
                Arguments.of(Map.of(Menu.ICE_CREAM, 1, Menu.CHOCOLATE_CAKE, 1)), // 20,000원
                Arguments.of(Map.of(Menu.T_BONE_STEAK, 3)), // 165,000원
                Arguments.of(Map.of(Menu.RED_WINE, 1)) // 60,000원
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenusAtLeastMinPrice")
    void 총_주문_금액이_이벤트_최소_금액을_만족하는_경우_이벤트를_적용한다(Map<Menu, Integer> menus) {
        // given
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(menus));

        // when
        boolean isEvent = totalPriceEventRule.isSatisfiedBy(reservation);

        // then
        assertThat(isEvent).isTrue();
    }

    static Stream<Arguments> provideMenusLessThanMinPrice() {
        return Stream.of(
                Arguments.of(Map.of(Menu.ICE_CREAM, 1)), // 5,000원
                Arguments.of(Map.of(Menu.ZERO_COKE, 3)), // 9,000원
                Arguments.of(Map.of(Menu.TAPAS, 1)) // 5,500원
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenusLessThanMinPrice")
    void 총_주문_금액이_이벤트_최소_금액을_만족하지_않는_경우_이벤트를_적용하지_않는다(Map<Menu, Integer> menus) {
        // given
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(menus));

        // when
        boolean isEvent = totalPriceEventRule.isSatisfiedBy(reservation);

        // then
        assertThat(isEvent).isFalse();
    }
}
