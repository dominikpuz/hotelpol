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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ReservationRepositoryTest {

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
        roomRepository.save(room1);
        roomRepository.save(room2);
    }

    @Test
    public void shouldFindReservationsWithTodayEndDate() {
        LocalDate now = LocalDate.now();
        ReservationEntity reservation = new ReservationEntity(room1, customer1, now.minusDays(3), 3);
        reservationRepository.save(reservation);
        ReservationEntity reservation2 = new ReservationEntity(room2, customer2, now, 2);
        reservationRepository.save(reservation2);

        List<ReservationEntity> reservations = reservationRepository.findReservationsByEndDateEquals(LocalDate.now());
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    public void shouldFindReservationsWithTodayStartDate() {
        LocalDate now = LocalDate.now();
        ReservationEntity reservation = new ReservationEntity(room1, customer1, now.minusDays(3), 3);
        reservationRepository.save(reservation);
        ReservationEntity reservation2 = new ReservationEntity(room2, customer2, now, 2);
        reservationRepository.save(reservation2);

        List<ReservationEntity> reservations = reservationRepository.findReservationsByStartDateEquals(LocalDate.now());
        assertEquals(1, reservations.size());
        assertEquals(reservation2, reservations.get(0));
    }
}
