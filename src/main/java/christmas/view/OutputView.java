package christmas.view;

import christmas.domain.EventResult;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.menu.Menu;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Consumer;

public class OutputView {
    private static final String START_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String EVENT_RESULT_HEADER = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String EMPTY_LINE = "";
    private static final String PRICE_FORMAT = "%,d원";
    private static final String MENU_FORMAT = "%s %d개";
    private static final String SECTION_HEADER_FORMAT = "<%s>";
    private static final String ORDER_MENU_HEADER = "주문 메뉴";
    private static final String TOTAL_PRICE_HEADER = "할인 전 총주문 금액";
    private static final String MENU_GIVEAWAY_HEADER = "증정 메뉴";

    private final Writer writer;

    public OutputView(Writer writer) {
        this.writer = writer;
    }

    private <T> void writeResultSection(String header, Consumer<T> writeResult, T result) {
        writer.writeLine(String.format(SECTION_HEADER_FORMAT, header));
        writeResult.accept(result);
        writer.writeLine(EMPTY_LINE);
    }

    public void writeStartMessage() {
        writer.writeLine(START_MESSAGE);
    }

    public void writeReservation(Reservation reservation) {
        writeEventResultHeader(reservation.getVisitDate());
        writeResultSection(ORDER_MENU_HEADER, this::writeOrderMenus, reservation.getOrderMenus());
        writeResultSection(TOTAL_PRICE_HEADER, this::writeTotalPrice, reservation.getTotalPrice());
    }

    private void writeEventResultHeader(LocalDate visitDate) {
        writer.writeLine(String.format(EVENT_RESULT_HEADER, visitDate.getMonthValue(), visitDate.getDayOfMonth()));
        writer.writeLine(EMPTY_LINE);
    }

    private void writeOrderMenus(Map<Menu, Integer> orderMenus) {
        orderMenus.forEach((menu, count) -> writer.writeLine(String.format(MENU_FORMAT, menu.getName(), count)));
    }

    private void writeTotalPrice(Money totalPrice) {
        writer.writeLine(String.format(PRICE_FORMAT, totalPrice.getAmount()));
    }

    public void writeEventResult(EventResult eventResult) {
        writeResultSection(MENU_GIVEAWAY_HEADER, this::writeMenuGiveaway, eventResult.getMenuGiveaway());
    }

    private void writeMenuGiveaway(Menu menuGiveaway) {
        writer.writeLine(menuGiveaway.getName());
    }
}
