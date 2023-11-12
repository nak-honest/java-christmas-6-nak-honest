package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.MainMenu;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ChristmasDDayDiscountTest {
    static IntStream provideDaysWithDiscount() {
        return IntStream.rangeClosed(1, 25);
    }

    @ParameterizedTest
    @MethodSource("provideDaysWithDiscount")
    void 방문_날짜가_1에서_25일_이라면_할인을_적용한다(int day) {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, day);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.T_BONE_STEAK, 1)));
        ChristmasDDayDiscount christmasDDayDiscount = new ChristmasDDayDiscount();

        // when
        Money actualDiscountAmount = christmasDDayDiscount.calculateDiscountAmount(reservation);

        // then
        Money expectedDiscountAmount = Money.of(1_000 + (day - 1) * 100);
        assertThat(actualDiscountAmount).isEqualTo(expectedDiscountAmount);
    }

    static IntStream provideDaysWithoutDiscount() {
        return IntStream.rangeClosed(26, 31);
    }

    @ParameterizedTest
    @MethodSource("provideDaysWithoutDiscount")
    void 방문_날짜가_26에서_31일_이라면_할인을_적용하지_않는다(int day) {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, day);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.T_BONE_STEAK, 1)));
        ChristmasDDayDiscount christmasDDayDiscount = new ChristmasDDayDiscount();

        // when
        Money actualDiscountAmount = christmasDDayDiscount.calculateDiscountAmount(reservation);

        // then
        Money expectedDiscountAmount = Money.of(0);
        assertThat(actualDiscountAmount).isEqualTo(expectedDiscountAmount);
    }
}
