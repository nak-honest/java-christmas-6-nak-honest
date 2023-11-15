package christmas.domain.reservation;

import static christmas.ErrorMessage.INVALID_MENU_ERROR;

import christmas.domain.Money;
import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMenus {
    private final Map<Menu, Integer> orderMenus;

    public OrderMenus(Map<Menu, Integer> orderMenus) {
        this.orderMenus = new EnumMap<>(orderMenus);
        OrderMenusValidator.validate(orderMenus);
    }

    public static OrderMenus createFromDistinctMenus(List<Map.Entry<Menu, Integer>> menus) {
        List<Map.Entry<Menu, Integer>> orderMenus = new ArrayList<>(menus);
        validateDuplicateMenu(orderMenus);

        return new OrderMenus(orderMenus.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private static void validateDuplicateMenu(List<Map.Entry<Menu, Integer>> orderMenus) {
        if (countDistinctMenu(orderMenus) != orderMenus.size()) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    private static int countDistinctMenu(List<Map.Entry<Menu, Integer>> orderMenus) {
        return (int) orderMenus.stream()
                .map(Map.Entry::getKey)
                .distinct()
                .count();
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

    public Money getTotalPrice() {
        return orderMenus.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(entry.getValue()))
                .reduce(Money.zeroInstance(), Money::add);
    }

    public Map<Menu, Integer> getOrderMenus() {
        return Collections.unmodifiableMap(orderMenus);
    }
}
