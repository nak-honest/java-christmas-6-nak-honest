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
                .filter(menuType::hasMenu)
                .mapToInt(orderMenus::get)
                .sum();
    }
}
