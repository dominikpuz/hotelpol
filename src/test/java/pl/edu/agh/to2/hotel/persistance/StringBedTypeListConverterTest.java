package pl.edu.agh.to2.hotel.persistance;

import org.junit.jupiter.api.Test;

import pl.edu.agh.to2.hotel.persistance.room.BedType;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class StringBedTypeListConverterTest {

    private final StringBedTypeListConverter converter = new StringBedTypeListConverter();

    @Test
    void convertToDatabaseColumn() {
        List<BedType> bedTypes = Arrays.asList(BedType.SINGLE_BED, BedType.DOUBLE_BED, BedType.SINGLE_BED);
        String result = converter.convertToDatabaseColumn(bedTypes);

        assertEquals("SINGLE_BED;DOUBLE_BED;SINGLE_BED", result);
    }

    @Test
    void convertToDatabaseColumnWithEmptyList() {
        List<BedType> bedTypes = List.of();
        String result = converter.convertToDatabaseColumn(bedTypes);

        assertEquals("", result);
    }

    @Test
    void convertToEntityAttribute() {
        String bedTypesString = "SINGLE_BED;DOUBLE_BED;SINGLE_BED";
        List<BedType> result = converter.convertToEntityAttribute(bedTypesString);

        List<BedType> expected = Arrays.asList(BedType.SINGLE_BED, BedType.DOUBLE_BED, BedType.SINGLE_BED);
        assertEquals(expected, result);
    }

    @Test
    void convertToEntityAttributeWithNullString() {
        List<BedType> result = converter.convertToEntityAttribute(null);

        assertEquals(List.of(), result);
    }

    @Test
    void convertToEntityAttributeWithEmptyString() {
        List<BedType> result = converter.convertToEntityAttribute("");

        assertEquals(List.of(), result);
    }
}

