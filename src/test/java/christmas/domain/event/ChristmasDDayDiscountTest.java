package christmas.domain.event;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.event.factory.ChristmasDDayDiscountFactory;
import christmas.domain.menu.Menu;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ChristmasDDayDiscountTest {
    static IntStream provideDiscountDaysAndDiscountAmount() {
        return IntStream.rangeClosed(1, 25);
    }

    @ParameterizedTest
    @MethodSource("provideDiscountDaysAndDiscountAmount")
    void 방문_날짜가_1일에서_25일_사이라면_크리스마스_디_데이_할인을_적용한다(int visitDay) {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, visitDay);
        Discount christmasDDayDiscount = ChristmasDDayDiscountFactory.create();
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        Money actualDiscountAmount = christmasDDayDiscount.discount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(1_000 + (visitDay - 1) * 100));
    }

    static IntStream provideNoDiscountDays() {
        return IntStream.rangeClosed(26, 31);
    }

    @ParameterizedTest
    @MethodSource("provideNoDiscountDays")
    void 방문_날짜가_26일에서_31일_사이라면_크리스마스_디_데이_할인을_적용하지_않는다(int visitDay) {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, visitDay);
        Discount christmasDDayDiscount = ChristmasDDayDiscountFactory.create();
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(Menu.BARBECUE_RIB, 1)));

        // when
        Money actualDiscountAmount = christmasDDayDiscount.discount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(Money.of(0));
    }
}
