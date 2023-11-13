package christmas.service;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.event.Discount;
import christmas.domain.event.Discounts;
import christmas.domain.menu.Menu;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountServiceTest {
    @Test
    void 총_할인_금액을_구할_수_있다() {
        // given
        List<Discount> discounts = List.of(
                new Discount("", reservation -> Money.of(40_000), reservation -> false),
                new Discount("", reservation -> Money.of(10_000), reservation -> true),
                new Discount("", reservation -> Money.of(5_000), reservation -> true)
                );

        Reservation reservation = new Reservation(LocalDate.now(), new OrderMenus(Map.of(Menu.RED_WINE, 1)));
        DiscountService discountService = new DiscountService(new Discounts(discounts));

        // when
        Money discountAmount = discountService.discount(reservation);

        // then
        assertThat(discountAmount).isEqualTo(Money.of(15_000));
    }
}
