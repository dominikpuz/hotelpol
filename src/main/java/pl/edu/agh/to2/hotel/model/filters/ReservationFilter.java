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
) implements IModelFilter {
    @Override
    public IModelFilter mergeFilter(IModelFilter filterToMerge) {
        if (!(filterToMerge instanceof ReservationFilter otherFilter)) {
            throw new IllegalArgumentException("Cannot merge filter of different class");
        }

        return ReservationFilter.builder()
                .roomId(mergeValues(roomId, otherFilter.roomId))
                .customerId(mergeValues(customerId, otherFilter.customerId))
                .startDate(mergeValues(startDate, otherFilter.startDate))
                .endDate(mergeValues(endDate, otherFilter.endDate))
                .reservationState(mergeValues(reservationState, otherFilter.reservationState))
                .build();
    }
}
