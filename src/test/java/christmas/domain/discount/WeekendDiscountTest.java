package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Money;
import christmas.domain.reservation.OrderMenus;
import christmas.domain.reservation.Reservation;
import christmas.domain.discount.factory.DaysOfWeekDiscountFactory;
import christmas.domain.menu.Menu;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class WeekendDiscountTest {
    static final LocalDate WEEKEND = LocalDate.of(2023, 12, 1);
    static final LocalDate WEEKDAY = LocalDate.of(2023, 12, 3);

    static Stream<Arguments> provideMenusAndDiscountAmount() {
        return Stream.of(
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 1), 2_023),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 1, Menu.T_BONE_STEAK, 1), 4_046),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 3), 6_069),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 2, Menu.TAPAS, 1), 4_046),
                Arguments.of(Map.of(Menu.ICE_CREAM, 3), 0),
                Arguments.of(Map.of(Menu.CAESAR_SALAD, 3), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenusAndDiscountAmount")
    void 방문_날짜가_주말인_경우_메인_메뉴_1개당_2_023원을_할인한다(Map<Menu, Integer> menus, int expectedDiscountAmount) {
        // given
        Discount weekendsDiscount = DaysOfWeekDiscountFactory.createWeekendsDiscount();
        Reservation reservation = new Reservation(WEEKEND, new OrderMenus(menus));

        // when
        Money actualDiscountAmount = weekendsDiscount.discount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(expectedDiscountAmount));
    }

    static Stream<Arguments> provideMenus() {
        return Stream.of(
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 1)),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 1, Menu.T_BONE_STEAK, 1)),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 3)),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 2, Menu.TAPAS, 1)),
                Arguments.of(Map.of(Menu.ICE_CREAM, 3)),
                Arguments.of(Map.of(Menu.CAESAR_SALAD, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenus")
    void 방문_날짜가_주말이_아닌_경우_주말_할인을_적용하지_않는다(Map<Menu, Integer> menus) {
        // given
        Discount weekendsDiscount = DaysOfWeekDiscountFactory.createWeekendsDiscount();
        Reservation reservation = new Reservation(WEEKDAY, new OrderMenus(menus));

        // when
        Money actualDiscountAmount = weekendsDiscount.discount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(0));
    }
}
