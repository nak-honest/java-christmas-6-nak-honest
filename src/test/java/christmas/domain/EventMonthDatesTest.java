package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Month;
import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class EventMonthDatesTest {
    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0, 32, 33})
    void 방문_날짜가_1일에서_31일_사이가_아니라면_예외를_발생시킨다(int visitDay) {
        // given
        EventMonthDates eventMonthDates = new EventMonthDates(2023, Month.DECEMBER);

        // when & then
        assertThatThrownBy(() -> eventMonthDates.from(visitDay))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    static IntStream provideValidDays() {
        return IntStream.rangeClosed(1, 31);
    }

    @ParameterizedTest
    @MethodSource("provideValidDays")
    void 방문_날짜가_1일에서_31일_사이라면_예외를_발생시키지_않는다(int visitDay) {
        // given
        EventMonthDates eventMonthDates = new EventMonthDates(2023, Month.DECEMBER);

        // when & then
        assertThatCode(() -> eventMonthDates.from(visitDay))
                .doesNotThrowAnyException();
    }
}
