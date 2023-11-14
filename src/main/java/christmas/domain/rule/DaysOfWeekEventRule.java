package christmas.domain.rule;

import christmas.domain.Reservation;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class DaysOfWeekEventRule implements EventRule {
    private final Set<DayOfWeek> eventDaysOfWeek;

    public DaysOfWeekEventRule(Set<DayOfWeek> eventDaysOfWeek) {
        this.eventDaysOfWeek = new HashSet<>(eventDaysOfWeek);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        return reservation.isDayOfWeekIn(eventDaysOfWeek);
    }
}
