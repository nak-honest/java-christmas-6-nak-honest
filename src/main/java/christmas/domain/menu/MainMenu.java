package christmas.domain.menu;

public enum MainMenu {
    T_BONE_STEAK(55_000);

    private final int price;

    MainMenu(int price) {
        this.price = price;
    }
}
