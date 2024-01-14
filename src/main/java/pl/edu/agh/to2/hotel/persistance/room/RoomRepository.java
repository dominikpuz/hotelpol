package pl.edu.agh.to2.hotel.persistance.room;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.to2.hotel.model.filters.RoomFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    @Query("SELECT r FROM RoomEntity r WHERE r.id NOT IN (SELECT DISTINCT res.room.id FROM ReservationEntity res WHERE " +
            "(:startDate <= res.startDate AND res.startDate < :endDate) " +
            "OR (:startDate < res.endDate AND res.endDate <= :endDate) " +
            "OR (:startDate >= res.startDate AND :endDate <= res.endDate) " +
            "OR (:startDate <= res.startDate AND :endDate >= res.endDate)" +
            ")")
    List<RoomEntity> findAvailableRoomsBetweenDates(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(r.id) <> 0 FROM RoomEntity r WHERE r.id = :roomId AND r.id NOT IN (SELECT DISTINCT res.room.id FROM ReservationEntity res WHERE (:startDate <= res.startDate AND res.startDate < :endDate) OR (:startDate < res.endDate AND res.endDate <= :endDate))")
    boolean isRoomAvailableBetweenDates(long roomId, LocalDate startDate, LocalDate endDate);

    Optional<RoomEntity> findRoomByRoomNumberAndFloor(String roomNumber, int floor);

    List<RoomEntity> findRoomEntitiesByRentPriceBetween(double min, double max);

    //TODO implement filtering rooms by comparing beds setup
    @Query("SELECT r FROM RoomEntity r WHERE " +
            "(:#{#filter.roomNumber()} IS NULL OR r.roomNumber ILIKE %:#{#filter.roomNumber()}%) " +
            "AND (:#{#filter.floor()} IS NULL OR r.floor = :#{#filter.floor()}) " +
           // "AND (:#{#filter.beds()} IS NULL OR :#{#filter.beds()} IN r.beds) " +
            "AND (:#{#filter.standard() == null ? null : 1} IS NULL OR r.roomStandard = :#{#filter.standard()}) " + // I REALLY don't understand why simple ":#{#filter.standard()}" doesn't work and I need to do... this... but I've lost enough time debugging it
            "AND (:#{#filter.minRentPrice()} IS NULL OR r.rentPrice >= :#{#filter.minRentPrice()}) " +
            "AND (:#{#filter.maxRentPrice()} IS NULL OR r.rentPrice <= :#{#filter.maxRentPrice()}) " +
            "AND (:#{#filter.startAvailabilityDate()} IS NULL OR :#{#filter.endAvailabilityDate()} IS NULL OR r.id NOT IN " +
                "(SELECT DISTINCT res.room.id FROM ReservationEntity res WHERE " +
                "(:#{#filter.startAvailabilityDate()} <= res.startDate AND res.startDate < :#{#filter.endAvailabilityDate()}) " +
                "OR (:#{#filter.startAvailabilityDate()} < res.endDate AND res.endDate <= :#{#filter.endAvailabilityDate()}) " +
                "OR (:#{#filter.startAvailabilityDate()} >= res.startDate AND :#{#filter.endAvailabilityDate()} <= res.endDate) " +
                "OR (:#{#filter.startAvailabilityDate()} <= res.startDate AND :#{#filter.endAvailabilityDate()} >= res.endDate)))")
    Page<RoomEntity> searchRooms(@Param("filter") RoomFilter filter, Pageable pageable);

}
