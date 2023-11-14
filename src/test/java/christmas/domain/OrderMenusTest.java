package christmas.domain;

import christmas.domain.menu.Menu;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderMenusTest {
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2})
    void 주문_메뉴의_개수가_자연수가_아니라면_예외를_발생시킨다(int count) {
        assertThatThrownBy(() -> new OrderMenus(Map.of(Menu.BARBECUE_RIB, count)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}
