package pl.edu.agh.to2.hotel.persistance.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationLogRepository extends JpaRepository<ReservationLogEntity, Long> {
}
