package pl.edu.agh.to2.hotel.persistance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLog;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.Reservation;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.agh.to2.hotel.TestUtils.sampleCustomer1;
import static pl.edu.agh.to2.hotel.TestUtils.sampleRoom1;

@SpringBootTest
public class ReservationLogRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationLogRepository reservationLogRepository;
    private Reservation sampleReservation1;

    @BeforeEach
    public void setUp() {
        reservationLogRepository.deleteAll();
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        customerRepository.deleteAll();

        customerRepository.save(sampleCustomer1);
        customerRepository.save(TestUtils.sampleCustomer2);
        roomRepository.save(sampleRoom1);
        roomRepository.save(TestUtils.sampleRoom2);
        sampleReservation1 = new Reservation(sampleRoom1, sampleCustomer1, LocalDate.now(), 2);
        reservationRepository.save(sampleReservation1);
    }

    @AfterEach
    public void clear() {
        reservationLogRepository.deleteAll();
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void shouldSaveReservationLogEntity() {
        ReservationLog log = new ReservationLog(
                sampleReservation1, sampleReservation1.getState(), ReservationState.PAID
        );

        reservationLogRepository.save(log);

        assertEquals(1, reservationLogRepository.count());
        assertTrue(reservationLogRepository.findAll().contains(log));
    }
}
