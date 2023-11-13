package christmas.service;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.event.Discount;
import christmas.domain.event.Discounts;

import java.util.Map;

public class DiscountService {
    private final Discounts discounts;

    public DiscountService(Discounts discounts) {
        this.discounts = discounts;
    }

    public Money discount(Reservation reservation) {
        return discounts.discount(reservation).values().stream()
                .reduce(Money.zeroInstance(), Money::add);
    }
}
