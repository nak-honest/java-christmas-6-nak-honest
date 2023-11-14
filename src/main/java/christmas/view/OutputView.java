package christmas.view;

import christmas.domain.Reservation;
import christmas.domain.menu.Menu;

import java.time.LocalDate;
import java.util.Map;

public class OutputView {
    private static final String EMPTY_LINE = "";
    private static final String START_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String EVENT_RESULT_HEADER = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDER_MENU_HEADER = "<주문 메뉴>";
    private static final String ORDER_MENU_FORMAT = "%s %d개";

    private final Writer writer;

    public OutputView(Writer writer) {
        this.writer = writer;
    }

    public void writeStartMessage() {
        writer.writeLine(START_MESSAGE);
    }

    public void writeReservation(Reservation reservation) {
        writeEventResultHeader(reservation.getVisitDate());
        writeOrderMenus(reservation.getOrderMenus());
    }

    private void writeEventResultHeader(LocalDate visitDate) {
        writer.writeLine(String.format(EVENT_RESULT_HEADER, visitDate.getMonthValue(), visitDate.getDayOfMonth()));
        writer.writeLine(EMPTY_LINE);
    }

    private void writeOrderMenus(Map<Menu, Integer> orderMenus) {
        writer.writeLine(ORDER_MENU_HEADER);
        orderMenus.forEach((menu, count) -> writer.writeLine(String.format(ORDER_MENU_FORMAT, menu.getName(), count)));
        writer.writeLine(EMPTY_LINE);
    }
}
