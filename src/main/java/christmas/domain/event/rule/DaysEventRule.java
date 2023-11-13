package christmas.domain.event.rule;

import christmas.domain.Reservation;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DaysEventRule implements EventRule {
    private final Set<LocalDate> eventDays;

    public DaysEventRule(Set<LocalDate> eventDays) {
        this.eventDays = new HashSet<>(eventDays);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        return reservation.isDayIn(eventDays);
    }
}
