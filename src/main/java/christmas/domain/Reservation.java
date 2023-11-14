package christmas.domain;

import christmas.domain.menu.MenuType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Reservation {
    private final LocalDate visitDate;
    private final OrderMenus orderMenus;

    public Reservation(LocalDate visitDate, OrderMenus orderMenus) {
        this.visitDate = visitDate;
        this.orderMenus = orderMenus;
    }

    public boolean isTotalPriceAtLeast(Money amount) {
        return orderMenus.isTotalPriceAtLeast(amount);
    }

    public boolean isDayIn(Collection<LocalDate> days) {
        return days.stream().anyMatch(visitDate::equals);
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

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public Map<String, Integer> getOrderMenus() {
        return orderMenus.getOrderMenus().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), Map.Entry::getValue));
    }

    public int getTotalPrice() {
        return orderMenus.getTotalPrice().getAmount();
    }
}
