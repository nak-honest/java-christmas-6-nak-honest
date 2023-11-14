package christmas.domain;

import christmas.domain.badge.EventBadge;
import christmas.domain.discount.DiscountResult;
import christmas.domain.menu.Menu;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EventResult {
    private static final int MENU_GIVEAWAY_COUNT = 1;

    private final DiscountResult discountResult;
    private final Menu menuGiveaway;
    private final EventBadge eventBadge;

    public EventResult(DiscountResult discountResult, Menu menuGiveaway, EventBadge eventBadge) {
        this.discountResult = discountResult;
        this.menuGiveaway = menuGiveaway;
        this.eventBadge = eventBadge;
    }

    public int getTotalBenefitAmount() {
        Money totalDiscountAmount = discountResult.getTotalDiscountAmounts();
        Money menuGiveawayPrice = menuGiveaway.getPrice();

        return totalDiscountAmount.add(menuGiveawayPrice).getAmount();
    }

    public int getDiscountAmount() {
        return discountResult.getTotalDiscountAmounts().getAmount();
    }

    public Map<String, Integer> getMenuGiveaway() {
        if (menuGiveaway == Menu.NONE) {
            return Collections.emptyMap();
        }

        return Map.of(menuGiveaway.getName(), MENU_GIVEAWAY_COUNT);
    }

    public Map<String, Integer> getBenefitResult() {
        Map<EventName, Money> benefitResult = new HashMap<>(discountResult.getDiscountResult());
        benefitResult.put(EventName.GIVEAWAY_EVENT, menuGiveaway.getPrice());

        return benefitResult.entrySet().stream()
                .filter(entry -> !entry.getValue().equals(Money.zeroInstance()))
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), entry -> entry.getValue().getAmount()));
    }

    public EventBadge getEventBadge() {
        return eventBadge;
    }
}
