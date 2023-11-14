package christmas.domain;

import christmas.domain.badge.EventBadge;
import christmas.domain.discount.DiscountResult;
import christmas.domain.menu.Menu;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EventResultTest {
    @Test
    void 총_혜택_금액을_계산한다() {
        // given
        DiscountResult discountResult = new DiscountResult(Map.of(
                EventName.CHRISTMAS_D_DAY, Money.of(2_000),
                EventName.SPECIAL, Money.of(1_000),
                EventName.WEEKDAY, Money.of(20_230)
        ));
        Menu menuGiveaway = Menu.CHAMPAGNE; // 25,000원
        EventResult eventResult = new EventResult(discountResult, menuGiveaway, EventBadge.SANTA);

        // when
        int totalBenefitAmount = eventResult.getTotalBenefitAmount();

        // then
        assertThat(totalBenefitAmount).isEqualTo(48_230);
    }
}
