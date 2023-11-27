package pl.edu.agh.to2.hotel.persistance.log;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "RESERVATION_LOGS")
@NoArgsConstructor
@Getter
@Setter
public class ReservationLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private LocalDateTime date;


    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_state")
    private ReservationState previousState;

    @Enumerated(EnumType.STRING)
    @Column(name = "updated_state")
    private ReservationState updatedState;

    public ReservationLogEntity(LocalDateTime date, ReservationEntity reservation, ReservationState previousState, ReservationState updatedState) {
        this.date = date;
        this.reservation = reservation;
        this.previousState = previousState;
        this.updatedState = updatedState;
    }

    public ReservationLogEntity(ReservationEntity reservation, ReservationState previousState, ReservationState updatedState) {
        this(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), reservation, previousState, updatedState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationLogEntity that = (ReservationLogEntity) o;
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
                ", reservation=" + reservation.getId() +
                ", previousState=" + previousState +
                ", updatedState=" + updatedState +
                '}';
    }
}
