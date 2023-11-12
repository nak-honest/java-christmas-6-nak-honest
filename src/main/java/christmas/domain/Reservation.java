package christmas.domain;

import christmas.domain.menu.MenuType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class Reservation {
    private final LocalDate visitDate;
    private final OrderMenus orderMenus;

    public Reservation(LocalDate visitDate, OrderMenus orderMenus) {
        this.visitDate = visitDate;
        this.orderMenus = orderMenus;
    }

    public boolean isBetween(LocalDate startDate, LocalDate endDate) {
        return !visitDate.isBefore(startDate) && !visitDate.isAfter(endDate);
    }

    public boolean isDayIn(Collection<LocalDate> days) {
        return days.stream()
                .anyMatch(visitDate::equals);
    }

    public boolean isDayOfWeekIn(Collection<DayOfWeek> daysOfWeek) {
        return daysOfWeek.stream()
                .anyMatch(this::isDayOfWeek);
    }

    private boolean isDayOfWeek(DayOfWeek dayOfWeek) {
        return visitDate.getDayOfWeek().equals(dayOfWeek);
    }

    public int daysFrom(LocalDate baseDate) {
        return (int) baseDate.datesUntil(visitDate).count();
    }

    public int countMenuByType(MenuType menuType) {
        return orderMenus.countMenuByType(menuType);
    }

    public int getVisitDay() {
        return visitDate.getDayOfMonth();
    }
}
