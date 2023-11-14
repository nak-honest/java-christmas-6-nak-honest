package christmas.view;

import static christmas.ErrorMessage.INVALID_DAY_ERROR;
import static christmas.ErrorMessage.INVALID_MENU_ERROR;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InputView {
    private static final String DAY_INPUT_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String MENU_INPUT_MESSAGE =
            "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String ORDER_ITEM_DELIMITER = ",";
    private static final String ORDER_MENU_COUNT_DELIMITER = "-";

    private final Supplier<String> reader;
    private final Writer writer;

    public InputView(Supplier<String> reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public int readVisitDay() {
        writer.writeLine(DAY_INPUT_MESSAGE);
        try {
            return Integer.parseInt(reader.get());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_DAY_ERROR.format());
        }
    }

    public Map<String, Integer> readMenus() {
        writer.writeLine(MENU_INPUT_MESSAGE);
        List<String> orderItems = splitOrderItems(reader.get());

        return orderItems.stream()
                .collect(Collectors.toMap(this::toMenuName, this::toOrderCount));
    }

    private List<String> splitOrderItems(String input) {
        return Arrays.asList(input.split(ORDER_ITEM_DELIMITER));
    }

    private String toMenuName(String orderItem) {
        return orderItem.split(ORDER_MENU_COUNT_DELIMITER)[0];
    }

    private Integer toOrderCount(String orderItem) {
        try {
            String orderCount = orderItem.split(ORDER_MENU_COUNT_DELIMITER)[1];
            return Integer.parseInt(orderCount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }
}
