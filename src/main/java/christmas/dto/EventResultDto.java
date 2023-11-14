package christmas.dto;

import christmas.domain.EventResult;
import christmas.domain.Reservation;
import christmas.domain.badge.EventBadge;

import java.time.LocalDate;
import java.util.Map;

public record EventResultDto(
        LocalDate visitDate,
        Map<String, Integer> orderMenus,
        int totalOrderPrice,
        Map<String, Integer> menuGiveaway,
        Map<String, Integer> benefitResult,
        int totalBenefitAmount,
        int paymentAmount,
        EventBadge eventBadge
) {
    public static EventResultDto of(Reservation reservation, EventResult eventResult) {
        return new EventResultDto(
                reservation.getVisitDate(),
                reservation.getOrderMenus(),
                reservation.getTotalPrice(),
                eventResult.getMenuGiveaway(),
                eventResult.getBenefitResult(),
                eventResult.getTotalBenefitAmount(),
                reservation.getTotalPrice() - eventResult.getDiscountAmount(),
                eventResult.getEventBadge()
        );
    }
}
