package pl.edu.agh.to2.hotel.persistance.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findReservationsByEndDateEquals(LocalDate endDate);

    List<ReservationEntity> findReservationsByStartDateEquals(LocalDate startDate);
}
