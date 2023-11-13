package christmas.view;

import static christmas.ErrorMessage.INVALID_DAY_ERROR;

import java.util.function.Supplier;

public class InputView {
    private static final String DAY_INPUT_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";

    private final Supplier<String> reader;
    private final Writer writer;

    public InputView(Supplier<String> reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public int readVisitDay() {
        writer.writeLine(DAY_INPUT_MESSAGE);

        return Integer.parseInt(reader.get());
    }
}
