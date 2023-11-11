package christmas.domain;

import java.time.LocalDate;

public class Reservation {
    private final LocalDate visitDate;
    private final OrderMenus orderMenus;

    public Reservation(LocalDate visitDate, OrderMenus orderMenus) {
        this.visitDate = visitDate;
        this.orderMenus = orderMenus;
    }

    public int getVisitDay() {
        return visitDate.getDayOfMonth();
    }
}
