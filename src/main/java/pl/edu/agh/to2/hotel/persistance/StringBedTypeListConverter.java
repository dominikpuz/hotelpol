package pl.edu.agh.to2.hotel.persistance;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edu.agh.to2.hotel.persistance.room.BedType;

import static java.util.Collections.*;

@Converter
public class StringBedTypeListConverter implements AttributeConverter<List<BedType>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<BedType> beds) {
        return beds.stream().map(BedType::name).collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<BedType> convertToEntityAttribute(String string) {
        if(string == null || string.isBlank()) return emptyList();
        return Arrays.stream(string.split(SPLIT_CHAR)).map(BedType::valueOf).toList();
    }
}