package pl.edu.agh.to2.hotel;

import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.util.Arrays;

import static pl.edu.agh.to2.hotel.persistance.room.BedType.DOUBLE_BED;
import static pl.edu.agh.to2.hotel.persistance.room.BedType.SINGLE_BED;

public class TestUtils {

    public static CustomerEntity createCustomerEntity1() {
        return new CustomerEntity("Jan", "Kowalski", "123321546", "jankowalski@gmail.com");
    }
    public static CustomerEntity createCustomerEntity2() {
        return new CustomerEntity("Natalia", "Wrona", "+48847392362", "nwrona@wp.pl");
    }

    public static RoomEntity createRoomEntity1() {
        return new RoomEntity("305A", 3, Arrays.asList(SINGLE_BED, DOUBLE_BED), RoomStandard.EXCLUSIVE, 200.0);
    }
    public static RoomEntity createRoomEntity2() {
        return new RoomEntity("201", 2, Arrays.asList(SINGLE_BED, SINGLE_BED), RoomStandard.CLASSIC, 130.0);
    }
}
