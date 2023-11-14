package christmas.view;

import christmas.domain.EventResult;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.EventName;
import christmas.domain.menu.Menu;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class OutputView {
    private static final String START_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String EVENT_RESULT_HEADER = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String NO_RESULT_MESSAGE = "없음";
    private static final String EMPTY_LINE = "";
    private static final String PRICE_FORMAT = "%,d원";
    private static final String MENU_FORMAT = "%s %d개";
    private static final String BENEFIT_RESULT_FORMAT = "%s: " + PRICE_FORMAT;
    private static final String SECTION_HEADER_FORMAT = "<%s>";
    private static final String ORDER_MENU_HEADER = "주문 메뉴";
    private static final String TOTAL_PRICE_HEADER = "할인 전 총주문 금액";
    private static final String MENU_GIVEAWAY_HEADER = "증정 메뉴";
    private static final String BENEFIT_RESULT_HEADER = "혜택 내역";
    private static final String BENEFIT_TOTAL_AMOUNT_HEADER = "총 혜택 금액";
    private static final int MENU_GIVEAWAY_COUNT = 1;

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
        writeResultSection(BENEFIT_RESULT_HEADER, this::writeBenefitResult, eventResult.getBenefitResult());
        writeResultSection(BENEFIT_TOTAL_AMOUNT_HEADER, this::writeTotalBenefitAmount,
                eventResult.getTotalBenefitAmount());
    }

    private void writeMenuGiveaway(Menu menuGiveaway) {
        if (menuGiveaway.equals(Menu.NONE)) {
            writer.writeLine(NO_RESULT_MESSAGE);
            return;
        }

        writer.writeLine(String.format(MENU_FORMAT, menuGiveaway.getName(), MENU_GIVEAWAY_COUNT));
    }

    private void writeBenefitResult(Map<EventName, Money> benefitResult) {
        Map<String, Integer> benefitResultNoZeroAmount = getBenefitResultNoZeroAmount(benefitResult);
        if (benefitResultNoZeroAmount.isEmpty()) {
            writer.writeLine(NO_RESULT_MESSAGE);
            return;
        }

        benefitResultNoZeroAmount.forEach((eventName, amount) ->
                writer.writeLine(String.format(BENEFIT_RESULT_FORMAT, eventName, amount)));
    }

    private Map<String, Integer> getBenefitResultNoZeroAmount(Map<EventName, Money> benefitResult) {
        return benefitResult.entrySet().stream()
                .filter(entry -> !entry.getValue().equals(Money.zeroInstance()))
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), entry -> -entry.getValue().getAmount()));
    }

    private void writeTotalBenefitAmount(Money totalBenefitAmount) {
        writer.writeLine(String.format(PRICE_FORMAT, -totalBenefitAmount.getAmount()));
    }
}
