package christmas.domain.discount.strategy;

import christmas.domain.Money;
import christmas.domain.reservation.Reservation;

public interface DiscountStrategy {
    Money calculateDiscountAmount(Reservation reservation);
}
