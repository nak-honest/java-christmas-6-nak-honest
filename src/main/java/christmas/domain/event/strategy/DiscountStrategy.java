package christmas.domain.event.strategy;

import christmas.domain.Money;
import christmas.domain.Reservation;

public interface DiscountStrategy {
    Money calculateDiscountAmount(Reservation reservation);
}
