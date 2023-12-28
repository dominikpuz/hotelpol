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

}
