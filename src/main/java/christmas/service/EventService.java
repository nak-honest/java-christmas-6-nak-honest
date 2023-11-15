package christmas.service;

import christmas.domain.EventResult;
import christmas.domain.Money;
import christmas.domain.reservation.Reservation;
import christmas.domain.badge.EventBadge;
import christmas.domain.discount.DiscountResult;
import christmas.domain.discount.Discounts;
import christmas.domain.giveaway.MenuGiveawayEvent;
import christmas.domain.menu.Menu;

public class EventService {
    private final Discounts discounts;
    private final MenuGiveawayEvent menuGiveawayEvent;

    public EventService(Discounts discounts, MenuGiveawayEvent menuGiveawayEvent) {
        this.discounts = discounts;
        this.menuGiveawayEvent = menuGiveawayEvent;
    }

    public EventResult getEventResult(Reservation reservation) {
        DiscountResult discountResult = discounts.discount(reservation);
        Menu menuGiveaway = menuGiveawayEvent.getMenuGiveaway(reservation);
        EventBadge eventBadge = EventBadge.of(calculateTotalBenefitAmount(discountResult, menuGiveaway));

        return new EventResult(discountResult, menuGiveaway, eventBadge);
    }

    private Money calculateTotalBenefitAmount(DiscountResult discountResult, Menu menuGiveaway) {
        Money totalDiscountAmount = discountResult.getTotalDiscountAmounts();
        Money menuGiveawayPrice = menuGiveaway.getPrice();
        
        return totalDiscountAmount.add(menuGiveawayPrice);
    }
}
