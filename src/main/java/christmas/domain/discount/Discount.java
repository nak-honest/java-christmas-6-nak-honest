package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.Reservation;

public interface Discount {
    Money calculateDiscountAmount(Reservation reservation);
}
