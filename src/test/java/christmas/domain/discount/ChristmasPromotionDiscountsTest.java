package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Money;
import christmas.domain.reservation.OrderMenus;
import christmas.domain.reservation.Reservation;
import christmas.domain.discount.factory.ChristmasPromotionDiscountsFactory;
import christmas.domain.menu.Menu;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ChristmasPromotionDiscountsTest {
    static final Discounts CHRISTMAS_PROMOTION_DISCOUNTS = ChristmasPromotionDiscountsFactory.create();

    @Test
    void 총주문_금액이_10_000원_이상이라면_할인을_적용한다() {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, 25);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.BARBECUE_RIB, 10)));

        // when
        DiscountResult discountResult = CHRISTMAS_PROMOTION_DISCOUNTS.discount(reservation);
        Money totalDiscountAmount = discountResult.getTotalDiscountAmounts();

        // then
        assertThat(totalDiscountAmount).isNotEqualTo(Money.of(0));
    }

    @Test
    void 총주문_금액이_10_000원_미만이라면_할인을_적용하지_않는다() {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, 25);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.TAPAS, 1)));

        // when
        DiscountResult discountResult = CHRISTMAS_PROMOTION_DISCOUNTS.discount(reservation);
        Money totalDiscountAmount = discountResult.getTotalDiscountAmounts();

        // then
        assertThat(totalDiscountAmount).isEqualTo(Money.of(0));
    }
}
