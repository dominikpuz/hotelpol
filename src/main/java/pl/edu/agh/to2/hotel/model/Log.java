package pl.edu.agh.to2.hotel.model;

import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;

import java.time.LocalDateTime;

public record Log(
        long id,
        LocalDateTime date,
        Reservation reservation,
        ReservationState updatedState
) {

    public Log(LocalDateTime date, Reservation reservation, ReservationState updatedState) {
        this(-1, date, reservation, updatedState);
    }
}