package christmas.controller;

import christmas.domain.EventResult;
import christmas.domain.Reservation;
import christmas.dto.EventResultDto;
import christmas.service.EventService;
import christmas.service.ReservationService;
import christmas.util.ExceptionRetryHandler;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EventPlanner {
    ReservationService reservationService;
    private final EventService eventService;
    private final InputView inputView;
    private final OutputView outputView;

    public EventPlanner(
            ReservationService reservationService,
            EventService eventService,
            InputView inputView,
            OutputView outputView
    ) {
        this.reservationService = reservationService;
        this.eventService = eventService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void planEvent() {
        outputView.writeStartMessage();
        Reservation reservation = reserve();
        EventResult eventResult = eventService.getEventResult(reservation);
        outputView.writeEventResult(EventResultDto.of(reservation, eventResult));
    }

    private Reservation reserve() {
        LocalDate visitDate = ExceptionRetryHandler.retryUntilValid(this::getVisitDate);

        return ExceptionRetryHandler.retryUntilValid(this::getReservation, visitDate);
    }

    private LocalDate getVisitDate() {
        int visitDay = inputView.readVisitDay();

        return reservationService.getVisitDate(visitDay);
    }

    private Reservation getReservation(LocalDate visitDate) {
        List<Map.Entry<String, Integer>> orderMenus = inputView.readOrderMenus();

        return reservationService.getReservation(visitDate, orderMenus);
    }
}
