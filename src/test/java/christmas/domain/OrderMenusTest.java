package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.menu.Menu;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderMenusTest {
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2})
    void 주문_메뉴의_개수가_자연수가_아니라면_예외를_발생시킨다(int count) {
        assertThatThrownBy(() -> new OrderMenus(Map.of(Menu.BARBECUE_RIB, count)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    static Stream<Arguments> provideMenusMoreThanMaxCount() {
        return Stream.of(
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 11, Menu.T_BONE_STEAK, 10)),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 21)),
                Arguments.of(Map.of(Menu.BARBECUE_RIB, 22))
        );
    }

    @ParameterizedTest
    @MethodSource("provideMenusMoreThanMaxCount")
    void 주문_메뉴의_총_개수가_20개보다_많다면_예외를_발생시킨다(Map<Menu, Integer> menus) {
        assertThatThrownBy(() -> new OrderMenus(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    static Stream<Arguments> provideDuplicateMenus() {
        return Stream.of(
                Arguments.of(List.of(Map.entry(Menu.BARBECUE_RIB, 1), Map.entry(Menu.BARBECUE_RIB, 1))),
                Arguments.of(List.of(Map.entry(Menu.ICE_CREAM, 1), Map.entry(Menu.ICE_CREAM, 3)))
        );
    }

    @ParameterizedTest
    @MethodSource("provideDuplicateMenus")
    void 중복된_메뉴가_있다면_예외를_발생시킨다(List<Map.Entry<Menu, Integer>> menus) {
        assertThatThrownBy(() -> OrderMenus.createFromDistinctMenus(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    static Stream<Arguments> provideOnlyBeverageMenus() {
        return Stream.of(
                Arguments.of(Map.of(Menu.ZERO_COKE, 3)),
                Arguments.of(Map.of(Menu.ZERO_COKE, 3, Menu.RED_WINE, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("provideOnlyBeverageMenus")
    void 음료_메뉴만_주문하면_예외를_발생시킨다(Map<Menu, Integer> menus) {
        assertThatThrownBy(() -> new OrderMenus(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}
