package christmas.domain.giveaway;

import christmas.domain.reservation.Reservation;
import christmas.domain.rule.EventRule;
import christmas.domain.menu.Menu;

public class MenuGiveawayEvent {
    private final Menu menuGiveaway;
    private final EventRule eventRule;

    public MenuGiveawayEvent(Menu menuGiveaway, EventRule eventRule) {
        this.menuGiveaway = menuGiveaway;
        this.eventRule = eventRule;
    }

    public Menu getMenuGiveaway(Reservation reservation) {
        if (!eventRule.isSatisfiedBy(reservation)) {
            return Menu.NONE;
        }

        return menuGiveaway;
    }
}
