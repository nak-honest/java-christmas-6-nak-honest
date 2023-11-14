package christmas.domain.discount.strategy;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.menu.MenuType;

public class MenuTypeDiscountStrategy implements DiscountStrategy {
    private final MenuType discountMenuType;
    private final Money discountAmountPerMenu;

    public MenuTypeDiscountStrategy(MenuType discountMenuType, Money discountAmountPerMenu) {
        this.discountMenuType = discountMenuType;
        this.discountAmountPerMenu = discountAmountPerMenu;
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        int discountMenuCount = reservation.countMenuByType(discountMenuType);

        return discountAmountPerMenu.multiply(discountMenuCount);
    }
}
