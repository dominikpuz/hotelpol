package pl.edu.agh.to2.hotel.model.filters;

import lombok.Builder;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.time.LocalDate;
import java.util.List;

@Builder
public record RoomFilter(
        String roomNumber,
        Integer floor,
        List<BedType> beds,
        RoomStandard standard,
        Double minRentPrice,
        Double maxRentPrice,
        LocalDate startAvailabilityDate,
        LocalDate endAvailabilityDate
) implements IModelFilter {
    @Override
    public IModelFilter mergeFilter(IModelFilter filterToMerge) {
        if (!(filterToMerge instanceof RoomFilter otherFilter)) {
            throw new IllegalArgumentException("Cannot merge filter of different class");
        }

        return RoomFilter.builder()
                .roomNumber(mergeValues(roomNumber, otherFilter.roomNumber))
                .floor(mergeValues(floor, otherFilter.floor))
                .beds(mergeValues(beds, otherFilter.beds))
                .standard(mergeValues(standard, otherFilter.standard))
                .minRentPrice(mergeValues(minRentPrice, otherFilter.minRentPrice))
                .maxRentPrice(mergeValues(maxRentPrice, otherFilter.maxRentPrice))
                .startAvailabilityDate(mergeValues(startAvailabilityDate, otherFilter.startAvailabilityDate))
                .endAvailabilityDate(mergeValues(endAvailabilityDate, otherFilter.endAvailabilityDate))
                .build();
    }
}