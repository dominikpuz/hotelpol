package pl.edu.agh.to2.hotel.persistance.reservation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.to2.hotel.model.filters.ReservationFilter;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findReservationsByEndDateEquals(LocalDate endDate);

    List<ReservationEntity> findReservationsByStartDateEquals(LocalDate startDate);

    @Query("SELECT r FROM ReservationEntity r WHERE " +
            "(:#{#filter.customerId()} IS NULL OR r.customer.id = :#{#filter.customerId()}) " +
            "AND (:#{#filter.roomId()} IS NULL OR r.room.id = :#{#filter.roomId()})" +
            "AND (:#{#filter.startDate()} IS NULL OR r.startDate >= :#{#filter.startDate()}) " +
            "AND (:#{#filter.endDate()} IS NULL OR r.endDate <= :#{#filter.endDate()}) " +
            "AND (:#{#filter.reservationState() == null ? null : 1} IS NULL OR r.state = :#{#filter.reservationState()})")  // I REALLY don't understand why simple ":#{#filter.standard()}" doesn't work and I need to do... this... but I've lost enough time debugging it
    List<ReservationEntity> searchReservations(@Param("filter") ReservationFilter filter, Pageable pageable);
}
