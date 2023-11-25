package pl.edu.agh.to2.hotel;

import pl.edu.agh.to2.hotel.persistance.customer.Customer;
import pl.edu.agh.to2.hotel.persistance.room.Room;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.util.Arrays;

import static pl.edu.agh.to2.hotel.persistance.room.BedType.DOUBLE_BED;
import static pl.edu.agh.to2.hotel.persistance.room.BedType.SINGLE_BED;

public class TestUtils {

    public static Customer sampleCustomer1 = new Customer("Jan", "Kowalski", "123321546", "jankowalski@gmail.com");
    public static Customer sampleCustomer2 = new Customer("Natalia", "Wrona", "+48847392362", "nwrona@wp.pl");

    public static Room sampleRoom1 = new Room("305A", 3, Arrays.asList(SINGLE_BED, DOUBLE_BED), RoomStandard.EXCLUSIVE, 200.0);
    public static Room sampleRoom2 = new Room("201", 2, Arrays.asList(SINGLE_BED, SINGLE_BED), RoomStandard.CLASSIC, 130.0);
}
