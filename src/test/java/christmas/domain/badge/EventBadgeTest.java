package christmas.domain.badge;

import christmas.domain.Money;
import christmas.domain.badge.EventBadge;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class EventBadgeTest {
    static Stream<Arguments> provideEventBadgeAndBenefitAmount() {
        return Stream.of(
                Arguments.of(EventBadge.NONE, 0),
                Arguments.of(EventBadge.NONE, 1_000),
                Arguments.of(EventBadge.NONE, 4_900),
                Arguments.of(EventBadge.STAR, 5_000),
                Arguments.of(EventBadge.STAR, 9_900),
                Arguments.of(EventBadge.TREE, 10_000),
                Arguments.of(EventBadge.TREE, 19_900),
                Arguments.of(EventBadge.SANTA, 20_000),
                Arguments.of(EventBadge.SANTA, 30_000)
        );
    }

    @ParameterizedTest
    @MethodSource("provideEventBadgeAndBenefitAmount")
    void 주어진_총_혜택_금액에_따라_이벤트_뱃지를_선정한다(EventBadge expectedEventBadge, int totalBenefitAmount) {
        // given
        Money givenTotalPrice = Money.of(totalBenefitAmount);

        // when
        EventBadge actualEventBadge = EventBadge.of(givenTotalPrice);

        // then
        assertThat(actualEventBadge).isEqualTo(expectedEventBadge);
    }
}
