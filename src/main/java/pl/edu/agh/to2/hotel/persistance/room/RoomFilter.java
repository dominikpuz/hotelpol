package pl.edu.agh.to2.hotel.persistance.room;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomFilter(
        String roomNumber,
        Integer floor,
        List<BedType> beds,
        RoomStandard standard,
        Double minRentPrice,
        Double maxRentPrice
) {

}