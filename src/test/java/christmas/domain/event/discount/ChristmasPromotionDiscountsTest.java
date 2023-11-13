package christmas.domain.event.discount;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.event.Discounts;
import christmas.domain.event.factory.ChristmasPromotionDiscountsFactory;
import christmas.domain.menu.Menu;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ChristmasPromotionDiscountsTest {
    static final Discounts CHRISTMAS_PROMOTION_DISCOUNTS = ChristmasPromotionDiscountsFactory.create();

    @Test
    void 총주문_금액이_10_000원_이상이라면_할인을_적용한다() {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, 25);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.BARBECUE_RIB, 10)));

        // when
        Money discountAmount = CHRISTMAS_PROMOTION_DISCOUNTS.discount(reservation).values().stream()
                .reduce(Money.of(0), Money::add);

        // then
        assertThat(discountAmount).isNotEqualTo(Money.of(0));
    }

    @Test
    void 총주문_금액이_10_000원_미만이라면_할인을_적용하지_않는다() {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, 25);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.ZERO_COKE, 1)));

        // when
        Money discountAmount = CHRISTMAS_PROMOTION_DISCOUNTS.discount(reservation).values().stream()
                .reduce(Money.of(0), Money::add);

        // then
        assertThat(discountAmount).isEqualTo(Money.of(0));
    }
}
