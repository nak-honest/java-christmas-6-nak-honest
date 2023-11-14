package christmas.domain.giveaway;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.Menu;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class MenuGiveawayEventTest {
    @Test
    void 주어진_이벤트_규칙을_만족하면_주어진_메뉴를_증정한다() {
        // given
        MenuGiveawayEvent menuGiveawayEvent = new MenuGiveawayEvent(Menu.CHAMPAGNE, reservation -> true);
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        Menu menuGiveaway = menuGiveawayEvent.getMenuGiveaway(reservation);

        // then
        assertThat(menuGiveaway).isEqualTo(Menu.CHAMPAGNE);
    }

    @Test
    void 주어진_이벤트_규칙을_만족하지_않으면_메뉴를_증정하지_않는다() {
        // given
        MenuGiveawayEvent menuGiveawayEvent = new MenuGiveawayEvent(Menu.CHAMPAGNE, reservation -> false);
        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        Menu menuGiveaway = menuGiveawayEvent.getMenuGiveaway(reservation);

        // then
        assertThat(menuGiveaway).isEqualTo(Menu.NONE);
    }
}
