package pl.edu.agh.to2.hotel.persistance;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;


@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerEntity customer1, customer2;
    private RoomEntity room1, room2;

    @BeforeEach
    public void setUp() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        customerRepository.deleteAll();

        customer1 = TestUtils.createCustomerEntity1();
        customer2 = TestUtils.createCustomerEntity2();

        room1 = TestUtils.createRoomEntity1();
        room2 = TestUtils.createRoomEntity2();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @Test
    public void shouldSaveRoomEntity() {
        roomRepository.save(room1);

        assertEquals(1, roomRepository.count());
        assertTrue(roomRepository.findAll().contains(room1));
    }

    @Test
    public void shouldFindRoomByRoomNumberAndFloor() {
        roomRepository.save(room1);
        roomRepository.save(room2);

        Optional<RoomEntity> found = roomRepository.findRoomByRoomNumberAndFloor("201", 2);
        assertTrue(found.isPresent());
        assertEquals(found.get(), room2);
    }

    @Test
    public void shouldNotFindRoomByRoomNumberAndFloor() {
        roomRepository.save(room1);
        roomRepository.save(room2);

        Optional<RoomEntity> found = roomRepository.findRoomByRoomNumberAndFloor("303", 3);
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindAvailableRooms() {
        roomRepository.save(room1);
        roomRepository.save(room2);

        ReservationEntity reservation1 = new ReservationEntity(
                room1, customer1, LocalDate.of(2023, 11, 20), 5
        );
        reservationRepository.save(reservation1);
        ReservationEntity reservation2 = new ReservationEntity(
                room1, customer2, LocalDate.of(2023, 11, 28), 3
        );
        reservationRepository.save(reservation2);
        ReservationEntity reservation3 = new ReservationEntity(
                room2, customer1, LocalDate.of(2023, 11, 26), 1
        );
        reservationRepository.save(reservation3);

        List<RoomEntity> availableRooms = roomRepository.findAvailableRoomsBetweenDates(LocalDate.of(2023, 11, 26), LocalDate.of(2023, 11, 28));

        assertEquals(1, availableRooms.size());
        assertTrue(availableRooms.contains(room1));
    }

    @Test
    public void shouldFindRoomsWithRentPriceBetween() {
        roomRepository.save(room1);
        roomRepository.save(room2);


        List<RoomEntity> rooms = roomRepository.findRoomEntitiesByRentPriceBetween(100, 150);

        assertEquals(1, rooms.size());
        assertTrue(rooms.contains(room2));
    }

}

