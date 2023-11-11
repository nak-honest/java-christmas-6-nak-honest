package christmas.domain;

import christmas.domain.menu.MainMenu;

import java.util.Map;

public class OrderMenus {
    private final Map<MainMenu, Integer> orderMenus;

    public OrderMenus(Map<MainMenu, Integer> orderMenus) {
        this.orderMenus = Map.copyOf(orderMenus);
    }
}
