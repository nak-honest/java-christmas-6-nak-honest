package christmas.domain.menu;

public enum AppetizerMenu implements Menu {
    BUTTON_MUSHROOM_SOUP("양송이수프", 6_000),
    TAPAS("타파스", 5_500),
    CAESAR_SALAD("시저샐러드", 8_000);

    private final String name;
    private final int price;

    AppetizerMenu(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
