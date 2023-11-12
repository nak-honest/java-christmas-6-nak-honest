package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.MainMenu;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SpecialDiscountTest {
    static List<Integer> provideDaysWithStar() {
        return List.of(3, 10, 17, 24, 25, 31);
    }

    @ParameterizedTest
    @MethodSource("provideDaysWithStar")
    void 방문_날짜가_별이_있는_날짜라면_특별_할인을_적용한다(int visitDay) {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, visitDay);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.T_BONE_STEAK, 1)));
        SpecialDiscount specialDiscount = new SpecialDiscount();

        // when
        Money actualDiscountAmount = specialDiscount.calculateDiscountAmount(reservation);

        // then
        Money expectedDiscountAmount = Money.of(1_000);
        assertThat(actualDiscountAmount).isEqualTo(expectedDiscountAmount);
    }

    static List<Integer> provideDaysWithoutStar() {
        return IntStream.rangeClosed(1, 31)
                .filter(day -> !provideDaysWithStar().contains(day))
                .boxed()
                .toList();
    }

    @ParameterizedTest
    @MethodSource("provideDaysWithoutStar")
    void 방문_날짜가_별이_없는_날짜라면_특별_할인을_적용하지_않는다(int visitDay) {
        // given
        LocalDate visitDate = LocalDate.of(2023, Month.DECEMBER, visitDay);
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(MainMenu.T_BONE_STEAK, 1)));
        SpecialDiscount specialDiscount = new SpecialDiscount();

        // when
        Money actualDiscountAmount = specialDiscount.calculateDiscountAmount(reservation);

        // then
        Money expectedDiscountAmount = Money.of(0);
        assertThat(actualDiscountAmount).isEqualTo(expectedDiscountAmount);
    }
}
