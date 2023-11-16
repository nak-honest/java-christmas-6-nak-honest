package christmas;

public enum ErrorMessage {
    INVALID_DAY_ERROR("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_MENU_ERROR("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    NEGATIVE_MONEY_ERROR("금액은 음수일 수 없습니다.");

    public static final String HEADER = "[ERROR]";

    private final String message;

    ErrorMessage(String bodyMessage) {
        this.message = String.format("%s %s", HEADER, bodyMessage);
    }

    public String format(Object ...args) {
        return String.format(message, args);
    }
}
