package christmas.domain;

import static christmas.ErrorMessage.INVALID_MENU_ERROR;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;

import java.util.Map;

public class OrderMenus {
    private static final int MAX_MENU_COUNT = 20;

    private final Map<Menu, Integer> orderMenus;

    public OrderMenus(Map<Menu, Integer> orderMenus) {
        this.orderMenus = Map.copyOf(orderMenus);
        validate();
    }

    private void validate() {
        validateMenuCount();
        validateTotalMenuCount();
    }

    private void validateMenuCount() {
        if (orderMenus.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    private void validateTotalMenuCount() {
        if (orderMenus.values().stream().mapToInt(Integer::intValue).sum() > MAX_MENU_COUNT) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    public int countMenuByType(MenuType menuType) {
        return orderMenus.keySet().stream()
                .filter(menu -> menu.isTypeOf(menuType))
                .mapToInt(orderMenus::get)
                .sum();
    }

    public boolean isTotalPriceAtLeast(Money amount) {
        return getTotalPrice().isGreaterThanOrEqual(amount);
    }

    private Money getTotalPrice() {
        return orderMenus.entrySet().stream()
                .map(this::getPrice)
                .reduce(Money.zeroInstance(), Money::add);
    }

    private Money getPrice(Map.Entry<Menu, Integer> entry) {
        Menu menu = entry.getKey();
        int count = entry.getValue();

        return menu.getPrice().multiply(count);
    }
}
