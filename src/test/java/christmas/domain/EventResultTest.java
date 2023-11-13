package christmas.domain;

import christmas.domain.badge.EventBadge;
import christmas.domain.discount.DiscountResult;
import christmas.domain.discount.DiscountType;
import christmas.domain.menu.Menu;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EventResultTest {
    @Test
    void 총_혜택_금액을_계산한다() {
        // given
        DiscountResult discountResult = new DiscountResult(Map.of(
                DiscountType.CHRISTMAS_D_DAY, Money.of(2_000),
                DiscountType.SPECIAL, Money.of(1_000),
                DiscountType.WEEKDAY, Money.of(20_230)
        ));
        Menu menuGiveaway = Menu.CHAMPAGNE; // 25,000원
        EventResult eventResult = new EventResult(discountResult, menuGiveaway, EventBadge.SANTA);

        // when
        Money totalBenefitAmount = eventResult.getTotalBenefitAmount();

        // then
        assertThat(totalBenefitAmount).isEqualTo(Money.of(48_230));
    }
}
