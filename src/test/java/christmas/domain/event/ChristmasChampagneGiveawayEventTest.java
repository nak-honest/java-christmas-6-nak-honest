package christmas.domain.event;

import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.event.factory.ChristmasChampagneGiveawayEventFactory;
import christmas.domain.menu.Menu;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ChristmasChampagneGiveawayEventTest {
    static MenuGiveawayEvent christmasChampagneGiveawayEvent = ChristmasChampagneGiveawayEventFactory.create();

    static Stream<Arguments> provideMenusAtLeastMinimumPrice() {
        return Stream.of(
                Arguments.of(Map.of(
                        Menu.CHAMPAGNE, 2, // 50,000원
                        Menu.RED_WINE, 3, // 180,000원
                        Menu.BARBECUE_RIB, 2 // 108,000원
                )),
                Arguments.of(Map.of(
                        Menu.RED_WINE, 2 // 120,000원
                ))
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenusAtLeastMinimumPrice")
    void 총주문_금액이_120_000원_이상이라면_샴페인을_증정한다(Map<Menu, Integer> menus) {
        // given
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(menus));

        // when
        Optional<Menu> menuGiveaway = christmasChampagneGiveawayEvent.getMenuGiveaway(reservation);

        // then
        assertThat(menuGiveaway).contains(Menu.CHAMPAGNE);
    }

    static Stream<Arguments> provideMenusLessThanMinimumPrice() {
        return Stream.of(
                Arguments.of(Map.of(
                        Menu.RED_WINE, 1, // 60,000원
                        Menu.BARBECUE_RIB, 1 // 54,000원
                )),
                Arguments.of(Map.of(
                        Menu.ICE_CREAM, 1 // 5,000원
                ))
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenusLessThanMinimumPrice")
    void 총주문_금액이_120_000원_미만이라면_샴페인을_증정하지_않는다(Map<Menu, Integer> menus) {
        // given
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(menus));

        // when
        Optional<Menu> menuGiveaway = christmasChampagneGiveawayEvent.getMenuGiveaway(reservation);

        // then
        assertThat(menuGiveaway).isEmpty();
    }
}
