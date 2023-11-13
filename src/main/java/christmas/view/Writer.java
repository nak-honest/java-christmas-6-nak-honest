package christmas.view;

import java.util.function.Consumer;

public class Writer {
    private final Consumer<String> writer;
    private final Consumer<String> newLineWriter;

    public Writer(Consumer<String> writer, Consumer<String> newLineWriter) {
        this.writer = writer;
        this.newLineWriter = newLineWriter;
    }

    public void write(String message) {
        writer.accept(message);
    }

    public void writeLine(String message) {
        newLineWriter.accept(message);
    }
}
