package christmas.domain;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;

import java.util.Map;

public class OrderMenus {
    private final Map<Menu, Integer> orderMenus;

    public OrderMenus(Map<Menu, Integer> orderMenus) {
        this.orderMenus = Map.copyOf(orderMenus);
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
