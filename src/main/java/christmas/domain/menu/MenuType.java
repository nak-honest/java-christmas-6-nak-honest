package christmas.domain.menu;

import java.util.List;

public enum MenuType {
    APPETIZER(AppetizerMenu.values()),
    MAIN(MainMenu.values()),
    BEVERAGE(BeverageMenu.values()),
    DESSERT(DessertMenu.values());

    private final List<Menu> menus;

    MenuType(Menu[] menus) {
        this.menus = List.of(menus);
    }

    public boolean hasMenu(Menu menu) {
        return menus.contains(menu);
    }
}
