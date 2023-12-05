package pl.edu.agh.to2.hotel.persistance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReservationRepositoryTest {

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
        roomRepository.save(TestUtils.sampleRoomEntity1);
        roomRepository.save(TestUtils.sampleRoomEntity2);
    }

    @Test
    public void shouldFindReservationsWithTodayEndDate() {
        LocalDate now = LocalDate.now();
        ReservationEntity reservation = new ReservationEntity(TestUtils.sampleRoomEntity1, TestUtils.sampleCustomerEntity1, now.minusDays(3), 3);
        reservationRepository.save(reservation);
        ReservationEntity reservation2 = new ReservationEntity(TestUtils.sampleRoomEntity2, TestUtils.sampleCustomerEntity2, now, 2);
        reservationRepository.save(reservation2);

        List<ReservationEntity> reservations = reservationRepository.findReservationsByEndDateEquals(LocalDate.now());
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    public void shouldFindReservationsWithTodayStartDate() {
        LocalDate now = LocalDate.now();
        ReservationEntity reservation = new ReservationEntity(TestUtils.sampleRoomEntity1, TestUtils.sampleCustomerEntity1, now.minusDays(3), 3);
        reservationRepository.save(reservation);
        ReservationEntity reservation2 = new ReservationEntity(TestUtils.sampleRoomEntity2, TestUtils.sampleCustomerEntity2, now, 2);
        reservationRepository.save(reservation2);

        List<ReservationEntity> reservations = reservationRepository.findReservationsByStartDateEquals(LocalDate.now());
        assertEquals(1, reservations.size());
        assertEquals(reservation2, reservations.get(0));
    }
}
