package pl.edu.agh.to2.hotel.model.filters;

import lombok.Builder;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;

import java.time.LocalDate;

@Builder
public record ReservationFilter(
        Long roomId,
        Long customerId,
        LocalDate startDate,
        LocalDate endDate,
        ReservationState reservationState
) implements IMergeableFilter<ReservationFilter> {

    @Override
    public ReservationFilter mergeFilter(ReservationFilter otherFilter) {
        if(otherFilter == null) return this;
        return ReservationFilter.builder()
                .roomId(mergeValues(roomId, otherFilter.roomId))
                .customerId(mergeValues(customerId, otherFilter.customerId))
                .startDate(mergeValues(startDate, otherFilter.startDate))
                .endDate(mergeValues(endDate, otherFilter.endDate))
                .reservationState(mergeValues(reservationState, otherFilter.reservationState))
                .build();
    }
}
