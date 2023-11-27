package pl.edu.agh.to2.hotel.persistance;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
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

        customerRepository.save(TestUtils.sampleCustomerEntity1);
        customerRepository.save(TestUtils.sampleCustomerEntity2);
    }

    @Test
    public void shouldSaveRoomEntity() {
        roomRepository.save(TestUtils.sampleRoomEntity1);

        assertEquals(1, roomRepository.count());
        assertTrue(roomRepository.findAll().contains(TestUtils.sampleRoomEntity1));
    }

    @Test
    public void shouldFindRoomByRoomNumberAndFloor() {
        roomRepository.save(TestUtils.sampleRoomEntity1);
        roomRepository.save(TestUtils.sampleRoomEntity2);

        Optional<RoomEntity> found = roomRepository.findRoomByRoomNumberAndFloor("201", 2);
        assertTrue(found.isPresent());
        assertEquals(found.get(), TestUtils.sampleRoomEntity2);
    }

    @Test
    public void shouldNotFindRoomByRoomNumberAndFloor() {
        roomRepository.save(TestUtils.sampleRoomEntity1);
        roomRepository.save(TestUtils.sampleRoomEntity2);

        Optional<RoomEntity> found = roomRepository.findRoomByRoomNumberAndFloor("303", 3);
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindAvailableRooms() {
        roomRepository.save(TestUtils.sampleRoomEntity1);
        roomRepository.save(TestUtils.sampleRoomEntity2);

        ReservationEntity reservation1 = new ReservationEntity(
                TestUtils.sampleRoomEntity1, TestUtils.sampleCustomerEntity1, LocalDate.of(2023, 11, 20), 5
        );
        reservationRepository.save(reservation1);
        ReservationEntity reservation2 = new ReservationEntity(
                TestUtils.sampleRoomEntity1, TestUtils.sampleCustomerEntity2, LocalDate.of(2023, 11, 28), 3
        );
        reservationRepository.save(reservation2);
        ReservationEntity reservation3 = new ReservationEntity(
                TestUtils.sampleRoomEntity2, TestUtils.sampleCustomerEntity1, LocalDate.of(2023, 11, 26), 1
        );
        reservationRepository.save(reservation3);

        List<RoomEntity> availableRooms = roomRepository.findAvailableRoomsBetweenDates(LocalDate.of(2023, 11, 26), LocalDate.of(2023, 11, 28));

        assertEquals(1, availableRooms.size());
        assertTrue(availableRooms.contains(TestUtils.sampleRoomEntity1));
    }

}

