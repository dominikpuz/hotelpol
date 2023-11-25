package pl.edu.agh.to2.hotel.persistance.room;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT DISTINCT res.room.id FROM Reservation res WHERE (:startDate <= res.startDate AND res.startDate < :endDate) OR (:startDate < res.endDate AND res.endDate <= :endDate))")
    List<Room> findAvailableRoomsBetweenDates(LocalDate startDate, LocalDate endDate);

    Optional<Room> findRoomByRoomNumberAndFloor(String roomNumber, int floor);

}
