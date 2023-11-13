package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.discount.DiscountResult;
import christmas.domain.discount.DiscountType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountResultTest {
    @Test
    void 총_할인_금액을_구할_수_있다() {
        // given
        Map<DiscountType, Money> discountAmounts = Map.of(
                DiscountType.SPECIAL, Money.of(1_000),
                DiscountType.WEEKDAY, Money.of(20_230),
                DiscountType.WEEKEND, Money.of(20_230)
        );
        DiscountResult discountResult = new DiscountResult(discountAmounts);

        // when
        Money totalDiscountAmount = discountResult.getTotalDiscountAmounts();

        // then
        assertThat(totalDiscountAmount).isEqualTo(Money.of(41_460));
    }
}
