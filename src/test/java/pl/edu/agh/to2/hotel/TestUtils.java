package pl.edu.agh.to2.hotel;

import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.util.Arrays;

import static pl.edu.agh.to2.hotel.persistance.room.BedType.DOUBLE_BED;
import static pl.edu.agh.to2.hotel.persistance.room.BedType.SINGLE_BED;

public class TestUtils {

    public static CustomerEntity sampleCustomerEntity1 = new CustomerEntity("Jan", "Kowalski", "123321546", "jankowalski@gmail.com");
    public static CustomerEntity sampleCustomerEntity2 = new CustomerEntity("Natalia", "Wrona", "+48847392362", "nwrona@wp.pl");

    public static RoomEntity sampleRoomEntity1 = new RoomEntity("305A", 3, Arrays.asList(SINGLE_BED, DOUBLE_BED), RoomStandard.EXCLUSIVE, 200.0);
    public static RoomEntity sampleRoomEntity2 = new RoomEntity("201", 2, Arrays.asList(SINGLE_BED, SINGLE_BED), RoomStandard.CLASSIC, 130.0);
}
