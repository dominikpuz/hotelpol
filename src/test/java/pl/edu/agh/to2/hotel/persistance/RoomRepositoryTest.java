package pl.edu.agh.to2.hotel.persistance;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.Reservation;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.room.Room;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        customerRepository.deleteAll();

        customerRepository.save(TestUtils.sampleCustomer1);
        customerRepository.save(TestUtils.sampleCustomer2);
    }

    @Test
    public void shouldSaveRoomEntity() {
        roomRepository.save(TestUtils.sampleRoom1);

        assertEquals(1, roomRepository.count());
        assertTrue(roomRepository.findAll().contains(TestUtils.sampleRoom1));
    }

    @Test
    public void shouldFindRoomByRoomNumberAndFloor() {
        roomRepository.save(TestUtils.sampleRoom1);
        roomRepository.save(TestUtils.sampleRoom2);

        Optional<Room> found = roomRepository.findRoomByRoomNumberAndFloor("201", 2);
        assertTrue(found.isPresent());
        assertEquals(found.get(), TestUtils.sampleRoom2);
    }

    @Test
    public void shouldNotFindRoomByRoomNumberAndFloor() {
        roomRepository.save(TestUtils.sampleRoom1);
        roomRepository.save(TestUtils.sampleRoom2);

        Optional<Room> found = roomRepository.findRoomByRoomNumberAndFloor("303", 3);
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindAvailableRooms() {
        roomRepository.save(TestUtils.sampleRoom1);
        roomRepository.save(TestUtils.sampleRoom2);

        Reservation reservation1 = new Reservation(
                TestUtils.sampleRoom1, TestUtils.sampleCustomer1, LocalDate.of(2023, 11, 20), 5
        );
        reservationRepository.save(reservation1);
        Reservation reservation2 = new Reservation(
                TestUtils.sampleRoom1, TestUtils.sampleCustomer2, LocalDate.of(2023, 11, 28), 3
        );
        reservationRepository.save(reservation2);
        Reservation reservation3 = new Reservation(
                TestUtils.sampleRoom2, TestUtils.sampleCustomer1, LocalDate.of(2023, 11, 26), 1
        );
        reservationRepository.save(reservation3);

        List<Room> availableRooms = roomRepository.findAvailableRoomsBetweenDates(LocalDate.of(2023, 11, 26), LocalDate.of(2023, 11, 28));

        assertEquals(1, availableRooms.size());
        assertTrue(availableRooms.contains(TestUtils.sampleRoom1));
    }

}

