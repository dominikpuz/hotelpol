package pl.edu.agh.to2.hotel.persistance.room;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    @Query("SELECT r FROM RoomEntity r WHERE r.id NOT IN (SELECT DISTINCT res.room.id FROM ReservationEntity res WHERE (:startDate <= res.startDate AND res.startDate < :endDate) OR (:startDate < res.endDate AND res.endDate <= :endDate))")
    List<RoomEntity> findAvailableRoomsBetweenDates(LocalDate startDate, LocalDate endDate);

    Optional<RoomEntity> findRoomByRoomNumberAndFloor(String roomNumber, int floor);

    List<RoomEntity> findRoomEntitiesByRentPriceBetween(double min, double max);

    //TODO implement filtering rooms by comparing beds setup
    @Query("SELECT r FROM RoomEntity r WHERE " +
            "(:#{#filter.roomNumber()} IS NULL OR r.roomNumber = :#{#filter.roomNumber()}) " +
            "AND (:#{#filter.floor()} IS NULL OR r.floor = :#{#filter.floor()}) " +
           // "AND (:#{#filter.beds()} IS NULL OR :#{#filter.beds()} IN r.beds) " +
            "AND (:#{#filter.standard()} IS NULL OR r.roomStandard = :#{#filter.standard()}) " +
            "AND (:#{#filter.minRentPrice()} IS NULL OR r.rentPrice >= :#{#filter.minRentPrice()}) " +
            "AND (:#{#filter.maxRentPrice()} IS NULL OR r.rentPrice <= :#{#filter.maxRentPrice()})")
    List<RoomEntity> searchRooms(@Param("filter") RoomFilter filter, Pageable pageable);

}
