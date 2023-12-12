package pl.edu.agh.to2.hotel.model;

import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;

import java.time.LocalDate;

@Getter
@Setter
public class Reservation {
    private final long id;
    private Room room;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationState state;

    public Reservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate, ReservationState state) {
        this(-1, room, customer, startDate, endDate, state);
    }

    public Reservation(long id, Room room, Customer customer, LocalDate startDate, LocalDate endDate, ReservationState state) {
        this.id = id;
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
    }

    public Reservation(Room room, Customer customer, LocalDate startDate, int days) {
        this(-1, room, customer, startDate, startDate.plusDays(days), ReservationState.CREATED);
    }

    public Reservation() {
        id = -1;
    }
}
