package christmas.service;

import christmas.domain.EventMonthDates;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.Menu;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReservationService {
    private final EventMonthDates eventMonthDates;

    public ReservationService(EventMonthDates eventMonthDates) {
        this.eventMonthDates = eventMonthDates;
    }

    public LocalDate getVisitDate(int visitDay) {
        return eventMonthDates.from(visitDay);
    }

    public Reservation getReservation(LocalDate visitDate, List<Map.Entry<String, Integer>> menus) {
        List<Map.Entry<Menu, Integer>> orderMenus = menus.stream()
                .map(menu -> Map.entry(Menu.of(menu.getKey()), menu.getValue()))
                .toList();

        return new Reservation(visitDate, OrderMenus.createFromDistinctMenus(orderMenus));
    }
}
