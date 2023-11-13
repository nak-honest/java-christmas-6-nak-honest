package christmas.domain.event;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.event.factory.SpecialDiscountFactory;
import christmas.domain.menu.MainMenu;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SpecialDiscountTest {
    static final Set<LocalDate> DATES_WITH_STAR = IntStream.rangeClosed(1, 31)
            .mapToObj(day -> LocalDate.of(2023, Month.DECEMBER, day))
            .filter(date -> date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfMonth() == 25)
            .collect(Collectors.toSet());

    static Set<LocalDate> provideDatesWithStar() {
        return DATES_WITH_STAR;
    }

    @ParameterizedTest
    @MethodSource("provideDatesWithStar")
    void 방문_날짜가_별이_있는_날짜라면_1_000원을_할인한다(LocalDate visitDate) {
        // given
        Discount specialDiscount = SpecialDiscountFactory.create();
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.BARBECUE_RIB, 1)));

        // when
        Money discountAmount = specialDiscount.discount(reservation);

        // then
        assertThat(discountAmount).isEqualTo(Money.of(1_000));
    }

    static Set<LocalDate> provideDatesWithoutStar() {
        return IntStream.rangeClosed(1, 31)
                .mapToObj(day -> LocalDate.of(2023, Month.DECEMBER, day))
                .filter(date -> !DATES_WITH_STAR.contains(date))
                .collect(Collectors.toSet());
    }

    @ParameterizedTest
    @MethodSource("provideDatesWithoutStar")
    void 방문_날짜가_별이_없는_날짜라면_할인을_적용하지_않는다(LocalDate visitDate) {
        // given
        Discount specialDiscount = SpecialDiscountFactory.create();
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.BARBECUE_RIB, 1)));

        // when
        Money discountAmount = specialDiscount.discount(reservation);

        // then
        assertThat(discountAmount).isEqualTo(Money.of(0));
    }
}
