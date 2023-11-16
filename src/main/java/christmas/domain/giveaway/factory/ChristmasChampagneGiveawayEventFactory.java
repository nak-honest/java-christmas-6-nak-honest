package christmas.domain.giveaway.factory;

import christmas.domain.Money;
import christmas.domain.giveaway.MenuGiveawayEvent;
import christmas.domain.rule.TotalPriceEventRule;
import christmas.domain.menu.Menu;

public class ChristmasChampagneGiveawayEventFactory {
    private static final Money MINIMUM_PRICE = Money.of(120_000);

    private ChristmasChampagneGiveawayEventFactory() {}

    public static MenuGiveawayEvent create() {
        return new MenuGiveawayEvent(Menu.CHAMPAGNE, new TotalPriceEventRule(MINIMUM_PRICE));
    }
}
