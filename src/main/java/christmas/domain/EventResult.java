package christmas.domain;

import christmas.domain.badge.EventBadge;
import christmas.domain.discount.DiscountResult;
import christmas.domain.menu.Menu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventResult {
    private final DiscountResult discountResult;
    private final Menu menuGiveaway;
    private final EventBadge eventBadge;

    public EventResult(DiscountResult discountResult, Menu menuGiveaway, EventBadge eventBadge) {
        this.discountResult = discountResult;
        this.menuGiveaway = menuGiveaway;
        this.eventBadge = eventBadge;
    }

    public Money getTotalBenefitAmount() {
        Money totalDiscountAmount = discountResult.getTotalDiscountAmounts();
        Money menuGiveawayPrice = menuGiveaway.getPrice();

        return totalDiscountAmount.add(menuGiveawayPrice);
    }

    public Menu getMenuGiveaway() {
        return menuGiveaway;
    }

    public Map<EventName, Money> getBenefitResult() {
        Map<EventName, Money> benefitResult = new HashMap<>(discountResult.getDiscountResult());
        benefitResult.put(EventName.GIVEAWAY, menuGiveaway.getPrice());

        return Collections.unmodifiableMap(benefitResult);
    }
}
