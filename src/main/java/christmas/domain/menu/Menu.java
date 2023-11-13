package christmas.domain.menu;

import static christmas.ErrorMessage.INVALID_MENU_ERROR;
import christmas.domain.Money;

import java.util.Arrays;

public enum Menu {
    BUTTON_MUSHROOM_SOUP("양송이수프", MenuType.APPETIZER, 6_000),
    TAPAS("타파스", MenuType.APPETIZER, 5_500),
    CAESAR_SALAD("시저샐러드", MenuType.APPETIZER, 8_000),
    T_BONE_STEAK("티본스테이크", MenuType.MAIN, 55_000),
    BARBECUE_RIB("바비큐립", MenuType.MAIN, 54_000),
    SEAFOOD_PASTA("해산물파스타", MenuType.MAIN, 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", MenuType.MAIN, 25_000),
    CHOCOLATE_CAKE("초코케이크", MenuType.DESSERT, 15_000),
    ICE_CREAM("아이스크림", MenuType.DESSERT, 5_000),
    ZERO_COKE("제로콜라", MenuType.BEVERAGE, 3_000),
    RED_WINE("레드와인", MenuType.BEVERAGE, 60_000),
    SHAM_PAIN("샴페인", MenuType.BEVERAGE, 25_000);

    private final String name;
    private final MenuType menuType;
    private final Money price;

    Menu(String name, MenuType menuType, int price) {
        this.name = name;
        this.menuType = menuType;
        this.price = Money.of(price);
    }

    public static Menu of(String name) {
        return Arrays.stream(values())
                .filter(menu -> menu.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_MENU_ERROR.format()));
    }

    public boolean isTypeOf(MenuType menuType) {
        return this.menuType.equals(menuType);
    }

    public Money getPrice() {
        return price;
    }
}
