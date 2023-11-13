package christmas.domain.event.discount;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.event.Discount;
import christmas.domain.event.factory.DaysOfWeekDiscountFactory;
import christmas.domain.menu.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WeekdaysDiscountTest {
    static final LocalDate WEEKEND = LocalDate.of(2023, 12, 1);
    static final LocalDate WEEKDAY = LocalDate.of(2023, 12, 3);

    static Stream<Arguments> provideMenusAndDiscountAmount() {
        return Stream.of(
                Arguments.of(Map.of(Menu.ICE_CREAM, 1), 2_023),
                Arguments.of(Map.of(Menu.ICE_CREAM, 1, Menu.CHOCOLATE_CAKE, 1), 4_046),
                Arguments.of(Map.of(Menu.ICE_CREAM, 3), 6_069),
                Arguments.of(Map.of(Menu.ICE_CREAM, 2, Menu.TAPAS, 1), 4_046),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 3), 0),
                Arguments.of(Map.of(Menu.RED_WINE, 3), 0),
                Arguments.of(Map.of(Menu.CAESAR_SALAD, 3), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenusAndDiscountAmount")
    void 방문_날짜가_평일인_경우_디저트_메뉴_1개당_2_023원을_할인한다(Map<Menu, Integer> menus, int expectedDiscountAmount) {
        // given
        Discount weekdaysDiscount = DaysOfWeekDiscountFactory.createWeekdaysDiscount();
        Reservation reservation = new Reservation(WEEKDAY, new OrderMenus(menus));

        // when
        Money actualDiscountAmount = weekdaysDiscount.discount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(expectedDiscountAmount));
    }

    static Stream<Arguments> provideMenus() {
        return Stream.of(
                Arguments.of(Map.of(Menu.ICE_CREAM, 1)),
                Arguments.of(Map.of(Menu.ICE_CREAM, 1, Menu.CHOCOLATE_CAKE, 1)),
                Arguments.of(Map.of(Menu.ICE_CREAM, 3)),
                Arguments.of(Map.of(Menu.ICE_CREAM, 2, Menu.TAPAS, 1)),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 3)),
                Arguments.of(Map.of(Menu.RED_WINE, 3)),
                Arguments.of(Map.of(Menu.CAESAR_SALAD, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenus")
    void 방문_날짜가_평일이_아닌_경우_평일_할인을_적용하지_않는다(Map<Menu, Integer> menus) {
        // given
        Discount weekdaysDiscount = DaysOfWeekDiscountFactory.createWeekdaysDiscount();
        Reservation reservation = new Reservation(WEEKEND, new OrderMenus(menus));

        // when
        Money actualDiscountAmount = weekdaysDiscount.discount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(0));
    }
}
