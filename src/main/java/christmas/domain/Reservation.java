package christmas.domain;

import christmas.domain.menu.MenuType;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Reservation {
    private final LocalDate visitDate;
    private final OrderMenus orderMenus;

    public Reservation(LocalDate visitDate, OrderMenus orderMenus) {
        this.visitDate = visitDate;
        this.orderMenus = orderMenus;
    }

    public boolean isDayOfWeek(DayOfWeek dayOfWeek) {
        return visitDate.getDayOfWeek().equals(dayOfWeek);
    }

    public int countMenuByType(MenuType menuType) {
        return orderMenus.countMenuByType(menuType);
    }

    public boolean isBetween(LocalDate startDate, LocalDate endDate) {
        return !visitDate.isBefore(startDate) && !visitDate.isAfter(endDate);
    }

    public int getVisitDay() {
        return visitDate.getDayOfMonth();
    }
}
