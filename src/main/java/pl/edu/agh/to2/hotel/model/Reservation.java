package pl.edu.agh.to2.hotel.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;

import java.time.LocalDate;

@Getter
public class Reservation {
    private final long id;
    private final ObjectProperty<Room> room;
    private final ObjectProperty<Customer> customer;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalDate> endDate;
    private final ObjectProperty<ReservationState> state;

    public Reservation() {
        id = -1;
        room = new SimpleObjectProperty<>();
        customer = new SimpleObjectProperty<>();
        startDate = new SimpleObjectProperty<>();
        endDate = new SimpleObjectProperty<>();
        state = new SimpleObjectProperty<>();
    }

    public Reservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate, ReservationState state) {
        this(-1, room, customer, startDate, endDate, state);
    }

    public Reservation(long id, Room room, Customer customer, LocalDate startDate, LocalDate endDate, ReservationState state) {
        this.id = id;
        this.room = new SimpleObjectProperty<>(room);
        this.customer = new SimpleObjectProperty<>(customer);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.state = new SimpleObjectProperty<>(state);
    }

    public Room getRoom() {
        return room.get();
    }

    public void setRoom(Room room) {
        this.room.set(room);
    }

    public Customer getCustomer() {
        return customer.get();
    }


    public void setCustomer(Customer customer) {
        this.customer.set(customer);
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public ReservationState getState() {
        return state.get();
    }

    public void setState(ReservationState state) {
        this.state.set(state);
    }
}
