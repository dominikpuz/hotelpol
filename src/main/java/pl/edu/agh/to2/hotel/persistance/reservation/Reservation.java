package pl.edu.agh.to2.hotel.persistance.reservation;

import jakarta.persistence.*;
import pl.edu.agh.to2.hotel.persistance.customer.Customer;
import pl.edu.agh.to2.hotel.persistance.room.Room;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "RESERVATIONS")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private ReservationState state;

    public Reservation() {
    }

    public Reservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate, ReservationState state) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
    }

    public Reservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        this(room, customer, startDate, endDate, ReservationState.CREATED);
    }

    public Reservation(Room room, Customer customer, LocalDate startDate, int days) {
        this(room, customer, startDate, startDate.plusDays(days));
    }

    public long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationState getState() {
        return state;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setState(ReservationState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", room=" + room +
                ", customer=" + customer +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(room, that.room) && Objects.equals(customer, that.customer) &&
                Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, customer, startDate, endDate, state);
    }
}
