package christmas.domain;

import static christmas.ErrorMessage.INVALID_MENU_ERROR;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;

import java.util.*;
import java.util.stream.Collectors;

public class OrderMenus {
    private static final int MIN_MENU_COUNT = 1;
    private static final int MAX_TOTAL_MENU_COUNT = 20;

    private final Map<Menu, Integer> orderMenus;

    public OrderMenus(Map<Menu, Integer> orderMenus) {
        this.orderMenus = new EnumMap<>(orderMenus);
        validate();
    }

    private void validate() {
        validateMenuCount();
        validateTotalMenuCount();
        validateOnlyBeverageOrder();
    }

    private void validateMenuCount() {
        if (orderMenus.values().stream().anyMatch(count -> count < MIN_MENU_COUNT)) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    private void validateTotalMenuCount() {
        if (orderMenus.values().stream().mapToInt(Integer::intValue).sum() > MAX_TOTAL_MENU_COUNT) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    private void validateOnlyBeverageOrder() {
        if (orderMenus.keySet().stream().allMatch(menu -> menu.isTypeOf(MenuType.BEVERAGE))) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    public static OrderMenus createFromDistinctMenus(List<Map.Entry<Menu, Integer>> menus) {
        List<Map.Entry<Menu, Integer>> orderMenus = new ArrayList<>(menus);
        validateDuplicateMenu(orderMenus);

        return new OrderMenus(orderMenus.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private static void validateDuplicateMenu(List<Map.Entry<Menu, Integer>> orderMenus) {
        if (orderMenus.stream().map(Map.Entry::getKey).distinct().count() != orderMenus.size()) {
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

    public Map<Menu, Integer> getOrderMenus() {
        return Collections.unmodifiableMap(orderMenus);
    }
}
