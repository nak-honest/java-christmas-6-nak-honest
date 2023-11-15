package christmas.domain.reservation;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;

import java.util.Map;

import static christmas.ErrorMessage.INVALID_MENU_ERROR;

public class OrderMenusValidator {
    private static final int MIN_MENU_COUNT = 1;
    private static final int MAX_TOTAL_MENU_COUNT = 20;

    private OrderMenusValidator() {}

    public static void validate(Map<Menu, Integer> orderMenus) {
        validateMenuCount(orderMenus);
        validateTotalMenuCount(orderMenus);
        validateOnlyBeverageOrder(orderMenus);
    }

    private static void validateMenuCount(Map<Menu, Integer> orderMenus) {
        if (isLessThanMinMenuCount(orderMenus)) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    private static boolean isLessThanMinMenuCount(Map<Menu, Integer> orderMenus) {
        return orderMenus.values().stream()
                .anyMatch(count -> count < MIN_MENU_COUNT);
    }

    private static void validateTotalMenuCount(Map<Menu, Integer> orderMenus) {
        if (isMoreThanMaxTotalMenuCount(orderMenus)) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    private static boolean isMoreThanMaxTotalMenuCount(Map<Menu, Integer> orderMenus) {
        return orderMenus.values().stream()
                .mapToInt(Integer::intValue)
                .sum() > MAX_TOTAL_MENU_COUNT;
    }

    private static void validateOnlyBeverageOrder(Map<Menu, Integer> orderMenus) {
        if (isOnlyBeverageOrder(orderMenus)) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    private static boolean isOnlyBeverageOrder(Map<Menu, Integer> orderMenus) {
        return orderMenus.keySet().stream()
                .allMatch(menu -> menu.isTypeOf(MenuType.BEVERAGE));
    }
}
