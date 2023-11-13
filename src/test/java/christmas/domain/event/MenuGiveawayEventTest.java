package christmas.domain.event;

import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.Menu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuGiveawayEventTest {
    @Test
    void 주어진_이벤트_규칙을_만족하면_주어진_메뉴를_증정한다() {
        // given
        MenuGiveawayEvent menuGiveawayEvent = new MenuGiveawayEvent(Menu.CHAMPAGNE, reservation -> true);
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        Optional<Menu> menuGiveaway = menuGiveawayEvent.getMenuGiveaway(reservation);

        // then
        assertThat(menuGiveaway).contains(Menu.CHAMPAGNE);
    }

    @Test
    void 주어진_이벤트_규칙을_만족하지_않으면_메뉴를_증정하지_않는다() {
        // given
        MenuGiveawayEvent menuGiveawayEvent = new MenuGiveawayEvent(Menu.CHAMPAGNE, reservation -> false);
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        Optional<Menu> menuGiveaway = menuGiveawayEvent.getMenuGiveaway(reservation);

        // then
        assertThat(menuGiveaway).isEmpty();
    }
}
