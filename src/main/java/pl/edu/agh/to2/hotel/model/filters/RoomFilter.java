package pl.edu.agh.to2.hotel.model.filters;

import lombok.Builder;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.util.List;

@Builder
public record RoomFilter(
        String roomNumber,
        Integer floor,
        List<BedType> beds,
        RoomStandard standard,
        Double minRentPrice,
        Double maxRentPrice
) implements IModelFilter {

}