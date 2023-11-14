package christmas;

import camp.nextstep.edu.missionutils.Console;
import christmas.controller.EventPlanner;
import christmas.domain.EventMonthDates;
import christmas.domain.discount.Discounts;
import christmas.domain.giveaway.MenuGiveawayEvent;
import christmas.domain.giveaway.factory.ChristmasChampagneGiveawayEventFactory;
import christmas.domain.discount.factory.ChristmasPromotionDiscountsFactory;
import christmas.service.EventService;
import christmas.service.ReservationService;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.Writer;

import java.time.Month;

public class Application {
    private static final int EVENT_YEAR = 2023;
    private static final Month EVENT_MONTH = Month.DECEMBER;

    public static void main(String[] args) {
        EventMonthDates eventMonthDates = new EventMonthDates(EVENT_YEAR, EVENT_MONTH);
        ReservationService reservationService = new ReservationService(eventMonthDates);

        Discounts christmasPromotionDiscounts = ChristmasPromotionDiscountsFactory.create();
        MenuGiveawayEvent christmasChampagneGiveawayEvent = ChristmasChampagneGiveawayEventFactory.create();

        EventService eventService = new EventService(christmasPromotionDiscounts, christmasChampagneGiveawayEvent);

        Writer writer = new Writer(System.out::print, System.out::println);
        InputView inputView = new InputView(Console::readLine, writer);
        OutputView outputView = new OutputView(writer);

        EventPlanner eventPlanner = new EventPlanner(reservationService, eventService, inputView, outputView);
        eventPlanner.planEvent();
    }
}
