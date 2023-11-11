package christmas.domain.discount;

import christmas.domain.Money;
import christmas.domain.OrderMenus;
import christmas.domain.Reservation;
import christmas.domain.menu.AppetizerMenu;
import christmas.domain.menu.BeverageMenu;
import christmas.domain.menu.DessertMenu;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DaysOfWeekDiscountTest {
    static final List<DayOfWeek> WEEKDAYS =
            List.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);

    static List<LocalDate> provideWeekdays() {
        return IntStream.rangeClosed(1, 31)
                .mapToObj(day -> LocalDate.of(2023, Month.DECEMBER, day))
                .filter(visitDate -> WEEKDAYS.contains(visitDate.getDayOfWeek()))
                .toList();
    }

    @ParameterizedTest
    @MethodSource("provideWeekdays")
    void 방문_날짜가_일요일에서_목요일_사이라면_평일_할인을_적용한다(LocalDate visitDate) {
        // given
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(DessertMenu.CHOCOLATE_CAKE, 1)));
        DaysOfWeekDiscount weekdaysDiscount = DaysOfWeekDiscount.weekdaysDiscount();

        // when
        Money actualDiscountAmount = weekdaysDiscount.calculateDiscountAmount(reservation);

        // then
        Money expectedDiscountAmount = new Money(2_023);
        assertThat(actualDiscountAmount).isEqualTo(expectedDiscountAmount);
    }

    static List<LocalDate> provideWeekends() {
        return IntStream.rangeClosed(1, 31)
                .mapToObj(day -> LocalDate.of(2023, Month.DECEMBER, day))
                .filter(visitDate -> !WEEKDAYS.contains(visitDate.getDayOfWeek()))
                .toList();
    }

    @ParameterizedTest
    @MethodSource("provideWeekends")
    void 방문_날짜가_일요일에서_목요일_사이가_아니라면_평일_할인을_적용하지_않는다(LocalDate visitDate) {
        // given
        Reservation reservation = new Reservation(visitDate, new OrderMenus(Map.of(DessertMenu.CHOCOLATE_CAKE, 1)));
        DaysOfWeekDiscount weekdaysDiscount = DaysOfWeekDiscount.weekdaysDiscount();

        // when
        Money actualDiscountAmount = weekdaysDiscount.calculateDiscountAmount(reservation);

        // then
        Money expectedDiscountAmount = new Money(0);
        assertThat(actualDiscountAmount).isEqualTo(expectedDiscountAmount);
    }

    static Stream<Arguments> provideOrderMenus() {
        return Stream.of(
                Arguments.of(
                        new OrderMenus(Map.of(BeverageMenu.RED_WINE, 1)),
                        new Money(0)),
                Arguments.of(
                        new OrderMenus(Map.of(DessertMenu.CHOCOLATE_CAKE, 2, AppetizerMenu.TAPAS, 5)),
                        new Money(4_046)),
                Arguments.of(
                        new OrderMenus(Map.of(DessertMenu.CHOCOLATE_CAKE, 2, DessertMenu.ICE_CREAM, 1)),
                        new Money(6_069))
        );
    }

    @ParameterizedTest
    @MethodSource("provideOrderMenus")
    void 평일_할인이_적용_되는_경우_디저트_메뉴의_수량에_따라_할인을_적용한다(OrderMenus orderMenus, Money expectedDiscountAmount) {
        // given
        Reservation reservation = new Reservation(LocalDate.of(2023, Month.DECEMBER, 4), orderMenus);
        DaysOfWeekDiscount weekdaysDiscount = DaysOfWeekDiscount.weekdaysDiscount();

        // when
        Money actualDiscountAmount = weekdaysDiscount.calculateDiscountAmount(reservation);

        // then
        assertThat(actualDiscountAmount).isEqualTo(expectedDiscountAmount);
    }
}
