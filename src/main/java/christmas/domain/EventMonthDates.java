package christmas.domain;

import static christmas.ErrorMessage.INVALID_DAY_ERROR;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class EventMonthDates {
    private static final int START_DAY = 1;

    private final int eventYear;
    private final Month eventMonth;
    private final int endDay;

    public EventMonthDates(int eventYear, Month eventMonth) {
        this.eventYear = eventYear;
        this.eventMonth = eventMonth;
        this.endDay = eventMonth.length(Year.isLeap(eventYear));
    }

    public LocalDate from(int day) {
        if (day < START_DAY || day > endDay) {
            throw new IllegalArgumentException(INVALID_DAY_ERROR.format());
        }

        return LocalDate.of(eventYear, eventMonth, day);
    }
}
