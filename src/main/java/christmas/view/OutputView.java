package christmas.view;

import christmas.domain.badge.EventBadge;
import christmas.dto.EventResultDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Consumer;

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
    private static final String BENEFIT_TOTAL_AMOUNT_HEADER = "총혜택 금액";
    private static final String PAYMENT_AMOUNT_HEADER = "할인 후 예상 결제 금액";
    private static final String EVENT_BADGE_HEADER = "12월 이벤트 배지";

    private final Writer writer;

    public OutputView(Writer writer) {
        this.writer = writer;
    }

    public void writeStartMessage() {
        writer.writeLine(START_MESSAGE);
    }

    public void writeEventResult(EventResultDto eventResultDto) {
        writeEventResultHeader(eventResultDto.visitDate());
        writeResultSection(ORDER_MENU_HEADER, this::writeOrderMenus, eventResultDto.orderMenus());
        writeResultSection(TOTAL_PRICE_HEADER, this::writePrice, eventResultDto.totalOrderPrice());
        writeResultSection(MENU_GIVEAWAY_HEADER, this::writeMenuGiveaway, eventResultDto.menuGiveaway());
        writeResultSection(BENEFIT_RESULT_HEADER, this::writeBenefitResult, eventResultDto.benefitResult());
        writeResultSection(BENEFIT_TOTAL_AMOUNT_HEADER, this::writeTotalBenefitAmount,
                eventResultDto.totalBenefitAmount());
        writeResultSection(PAYMENT_AMOUNT_HEADER, this::writePrice, eventResultDto.paymentAmount());
        writeResultSection(EVENT_BADGE_HEADER, this::writeEventBadge, eventResultDto.eventBadge());
    }

    private void writeEventResultHeader(LocalDate visitDate) {
        writer.writeLine(String.format(EVENT_RESULT_HEADER, visitDate.getMonthValue(), visitDate.getDayOfMonth()));
        writer.writeLine(EMPTY_LINE);
    }

    private <T> void writeResultSection(String header, Consumer<T> writeResult, T result) {
        writer.writeLine(String.format(SECTION_HEADER_FORMAT, header));
        writeResult.accept(result);
        writer.writeLine(EMPTY_LINE);
    }

    private void writeOrderMenus(Map<String, Integer> orderMenus) {
        orderMenus.forEach((menuName, count) -> writer.writeLine(String.format(MENU_FORMAT, menuName, count)));
    }

    private void writePrice(int price) {
        writer.writeLine(String.format(PRICE_FORMAT, price));
    }

    private void writeMenuGiveaway(Map<String, Integer> menuGiveaway) {
        if (menuGiveaway.isEmpty()) {
            writer.writeLine(NO_RESULT_MESSAGE);
            return;
        }

        writeOrderMenus(menuGiveaway);
    }

    private void writeBenefitResult(Map<String, Integer> benefitResult) {
        if (benefitResult.isEmpty()) {
            writer.writeLine(NO_RESULT_MESSAGE);
            return;
        }

        benefitResult.forEach((eventName, amount) ->
                writer.writeLine(String.format(BENEFIT_RESULT_FORMAT, eventName, -amount)));
    }

    private void writeTotalBenefitAmount(int totalBenefitAmount) {
        writer.writeLine(String.format(PRICE_FORMAT, -totalBenefitAmount));
    }

    private void writeEventBadge(EventBadge eventBadge) {
        if (eventBadge.equals(EventBadge.NONE)) {
            writer.writeLine(NO_RESULT_MESSAGE);
            return;
        }

        writer.writeLine(eventBadge.getName());
    }
}
