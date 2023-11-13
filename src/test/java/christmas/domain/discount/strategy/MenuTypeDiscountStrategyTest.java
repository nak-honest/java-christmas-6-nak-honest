package christmas.domain.discount.strategy;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuTypeDiscountStrategyTest {
    static final MenuType DISCOUNT_MENU_TYPE = MenuType.MAIN;
    static final Money DISCOUNT_AMOUNT_PER_MENU = Money.of(1_000);

    static Stream<Arguments> provideMenusAndDiscountAmount() {
        return Stream.of(
                Arguments.of(Map.of(BeverageMenu.RED_WINE, 2), 0),
                Arguments.of(Map.of(MainMenu.BARBECUE_RIB, 1), 1_000),
                Arguments.of(Map.of(MainMenu.BARBECUE_RIB, 2), 2_000),
                Arguments.of(Map.of(BeverageMenu.RED_WINE, 2, MainMenu.BARBECUE_RIB, 1), 1_000),
                Arguments.of(Map.of(MainMenu.BARBECUE_RIB, 2, MainMenu.T_BONE_STEAK, 1), 3_000)
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenusAndDiscountAmount")
    void 주어진_메뉴_타입의_개수만큼_할인_금액을_계산한다(Map<Menu, Integer> menus, int expectedDiscountAmount) {
        // given
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(menus));
        MenuTypeDiscountStrategy menuTypeDiscountStrategy =
                new MenuTypeDiscountStrategy(DISCOUNT_MENU_TYPE, DISCOUNT_AMOUNT_PER_MENU);

        // when
        Money actualDiscountAmount = menuTypeDiscountStrategy.calculateDiscountAmount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(expectedDiscountAmount));
    }
}
