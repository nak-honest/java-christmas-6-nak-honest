package christmas.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class ExceptionRetryHandler {
    private ExceptionRetryHandler() {}

    public static <R> R retryUntilValid(Supplier<R> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
            }
        }
    }

    public static <T, R> R retryUntilValid(Function<T, R> function, T argument) {
        while (true) {
            try {
                return function.apply(argument);
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
