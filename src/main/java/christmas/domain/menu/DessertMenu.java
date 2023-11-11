package christmas.domain.menu;

public enum DessertMenu implements Menu {
    CHOCOLATE_CAKE("초코케이크", 15_000),
    ICE_CREAM("아이스크림", 5_000);

    private final String name;
    private final int price;

    DessertMenu(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
