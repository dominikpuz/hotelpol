package pl.edu.agh.to2.hotel.persistance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.Reservation;
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

        customerRepository.save(TestUtils.sampleCustomer1);
        customerRepository.save(TestUtils.sampleCustomer2);
        roomRepository.save(TestUtils.sampleRoom1);
        roomRepository.save(TestUtils.sampleRoom2);
    }

    @Test
    public void shouldFindReservationsWithTodayEndDate() {
        LocalDate now = LocalDate.now();
        Reservation reservation = new Reservation(TestUtils.sampleRoom1, TestUtils.sampleCustomer1, now.minusDays(3), 3);
        reservationRepository.save(reservation);
        Reservation reservation2 = new Reservation(TestUtils.sampleRoom2, TestUtils.sampleCustomer2, now, 2);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.findReservationsByEndDateEquals(LocalDate.now());
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    public void shouldFindReservationsWithTodayStartDate() {
        LocalDate now = LocalDate.now();
        Reservation reservation = new Reservation(TestUtils.sampleRoom1, TestUtils.sampleCustomer1, now.minusDays(3), 3);
        reservationRepository.save(reservation);
        Reservation reservation2 = new Reservation(TestUtils.sampleRoom2, TestUtils.sampleCustomer2, now, 2);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.findReservationsByStartDateEquals(LocalDate.now());
        assertEquals(1, reservations.size());
        assertEquals(reservation2, reservations.get(0));
    }
}
