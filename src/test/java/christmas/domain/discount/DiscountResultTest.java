package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.EventName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountResultTest {
    @Test
    void 총_할인_금액을_구할_수_있다() {
        // given
        Map<EventName, Money> discountAmounts = Map.of(
                EventName.SPECIAL, Money.of(1_000),
                EventName.WEEKDAY, Money.of(20_230),
                EventName.WEEKEND, Money.of(20_230)
        );
        DiscountResult discountResult = new DiscountResult(discountAmounts);

        // when
        Money totalDiscountAmount = discountResult.getTotalDiscountAmounts();

        // then
        assertThat(totalDiscountAmount).isEqualTo(Money.of(41_460));
    }
}
