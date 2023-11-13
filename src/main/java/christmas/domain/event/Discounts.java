package christmas.domain.event;

import christmas.domain.Money;
import christmas.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Discounts {
    private final List<Discount> discounts;

    public Discounts(List<Discount> discounts) {
        this.discounts = new ArrayList<>(discounts);
    }

    public Map<Discount, Money> discount(Reservation reservation) {
        return discounts.stream()
                .collect(Collectors.toMap(Function.identity(), discount -> discount.discount(reservation)));
    }
}
