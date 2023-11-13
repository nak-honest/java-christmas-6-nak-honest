package christmas.domain.event;

import christmas.domain.Reservation;
import christmas.domain.event.rule.EventRule;
import christmas.domain.menu.Menu;

import java.util.Optional;

public class MenuGiveawayEvent {
    private final Menu menuGiveaway;
    private final EventRule eventRule;

    public MenuGiveawayEvent(Menu menuGiveaway, EventRule eventRule) {
        this.menuGiveaway = menuGiveaway;
        this.eventRule = eventRule;
    }

    public Optional<Menu> getMenuGiveaway(Reservation reservation) {
        if (!eventRule.isSatisfiedBy(reservation)) {
            return Optional.empty();
        }

        return Optional.of(menuGiveaway);
    }
}
