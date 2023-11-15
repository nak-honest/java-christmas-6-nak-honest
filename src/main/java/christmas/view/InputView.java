package christmas.view;

import static christmas.ErrorMessage.INVALID_DAY_ERROR;
import static christmas.ErrorMessage.INVALID_MENU_ERROR;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class InputView {
    private static final String DAY_INPUT_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String MENU_INPUT_MESSAGE =
            "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String ORDER_ITEM_DELIMITER = ",";
    private static final String ORDER_MENU_COUNT_DELIMITER = "-";
    private static final int MENU_ITEM_INDEX_COUNT = 2;
    private static final int MENU_NAME_INDEX = 0;
    private static final int MENU_COUNT_INDEX = 1;

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

    public List<Map.Entry<String, Integer>> readOrderMenus() {
        writer.writeLine(MENU_INPUT_MESSAGE);

        List<String> orderItems = splitOrderItems(reader.get());
        List<List<String>> orderMenus = orderItems.stream()
                .map(this::splitOrderItem)
                .toList();

        return orderMenus.stream()
                .map(orderMenu ->
                        Map.entry(orderMenu.get(MENU_NAME_INDEX), toOrderCount(orderMenu.get(MENU_COUNT_INDEX))))
                .toList();
    }

    private List<String> splitOrderItems(String orderItems) {
        validateDelimiter(orderItems, ORDER_ITEM_DELIMITER);

        return Arrays.asList(orderItems.split(ORDER_ITEM_DELIMITER));
    }

    private List<String> splitOrderItem(String orderItem) {
        validateDelimiter(orderItem, ORDER_MENU_COUNT_DELIMITER);
        List<String> orderMenuAndCount = Arrays.asList(orderItem.split(ORDER_MENU_COUNT_DELIMITER));

        if (orderMenuAndCount.size() != MENU_ITEM_INDEX_COUNT) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }

        return orderMenuAndCount;
    }

    private void validateDelimiter(String input, String delimiter) {
        if (input.startsWith(delimiter) || input.endsWith(delimiter)) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }

    private Integer toOrderCount(String menuCount) {
        try {
            return Integer.parseInt(menuCount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR.format());
        }
    }
}
