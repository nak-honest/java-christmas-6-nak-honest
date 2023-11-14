package christmas.domain.menu;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MenuTest {
    @ParameterizedTest
    @ValueSource(strings = {"피자", "", " ", "쌀아이스크림"})
    void 올바르지_않은_이름이_주어지면_예외가_발생한다(String name) {
        assertThatCode(() -> Menu.of(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "티본스테이크", "아이스크림"})
    void 올바른_이름이_주어지면_메뉴를_얻을_수_있다(String name) {
        assertThatCode(() -> Menu.of(name))
                .doesNotThrowAnyException();
    }
}
