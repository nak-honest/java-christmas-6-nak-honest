package christmas.domain.menu;

public enum BeverageMenu implements Menu {
    ZERO_COKE("제로콜라", 3_000),
    RED_WINE("레드와인", 60_000),
    SHAM_PAIN("샴페인", 25_000);

    private final String name;
    private final int price;

    BeverageMenu(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
