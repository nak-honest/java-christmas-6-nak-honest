package christmas.domain.event.factory;

import christmas.domain.Money;
import christmas.domain.event.MenuGiveawayEvent;
import christmas.domain.event.rule.TotalPriceEventRule;
import christmas.domain.menu.Menu;

public class ChristmasChampagneGiveawayEventFactory {
    private static final Money MINIMUM_PRICE = Money.of(120_000);

    public static MenuGiveawayEvent create() {
        return new MenuGiveawayEvent(Menu.CHAMPAGNE, new TotalPriceEventRule(MINIMUM_PRICE));
    }
}
