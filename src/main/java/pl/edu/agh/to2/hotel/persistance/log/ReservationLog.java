package pl.edu.agh.to2.hotel.persistance.log;

import jakarta.persistence.*;
import pl.edu.agh.to2.hotel.persistance.reservation.Reservation;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Objects;

@Entity
@Table(name = "RESERVATION_LOGS")
public class ReservationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_state")
    private ReservationState previousState;

    @Enumerated(EnumType.STRING)
    @Column(name = "updated_state")
    private ReservationState updatedState;

    public ReservationLog() {
    }

    public ReservationLog(LocalDateTime date, Reservation reservation, ReservationState previousState, ReservationState updatedState) {
        this.date = date;
        this.reservation = reservation;
        this.previousState = previousState;
        this.updatedState = updatedState;
    }

    public ReservationLog(Reservation reservation, ReservationState previousState, ReservationState updatedState) {
        this(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), reservation, previousState, updatedState);
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public ReservationState getPreviousState() {
        return previousState;
    }

    public ReservationState getUpdatedState() {
        return updatedState;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setPreviousState(ReservationState previousState) {
        this.previousState = previousState;
    }

    public void setUpdatedState(ReservationState updatedState) {
        this.updatedState = updatedState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationLog that = (ReservationLog) o;
        return date.isEqual(that.date) && Objects.equals(reservation, that.reservation) && previousState == that.previousState && updatedState == that.updatedState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, reservation, previousState, updatedState);
    }

    @Override
    public String toString() {
        return "ReservationLog{" +
                "id=" + id +
                ", date=" + date +
                ", reservation=" + reservation +
                ", previousState=" + previousState +
                ", updatedState=" + updatedState +
                '}';
    }
}
