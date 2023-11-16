package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Money;
import christmas.domain.EventName;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class DiscountResultTest {
    @Test
    void 총_할인_금액을_구할_수_있다() {
        // given
        Map<EventName, Money> discountAmounts = Map.of(
                EventName.SPECIAL_DISCOUNT, Money.of(1_000),
                EventName.WEEKDAY_DISCOUNT, Money.of(20_230),
                EventName.WEEKEND_DISCOUNT, Money.of(20_230)
        );
        DiscountResult discountResult = new DiscountResult(discountAmounts);

        // when
        Money totalDiscountAmount = discountResult.getTotalDiscountAmounts();

        // then
        assertThat(totalDiscountAmount).isEqualTo(Money.of(41_460));
    }
}
