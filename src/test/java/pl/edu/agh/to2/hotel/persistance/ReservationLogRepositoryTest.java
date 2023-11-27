package pl.edu.agh.to2.hotel.persistance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogEntity;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.agh.to2.hotel.TestUtils.sampleCustomerEntity1;
import static pl.edu.agh.to2.hotel.TestUtils.sampleRoomEntity1;

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
    private ReservationEntity sampleReservation1;

    @BeforeEach
    public void setUp() {
        reservationLogRepository.deleteAll();
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        customerRepository.deleteAll();

        customerRepository.save(sampleCustomerEntity1);
        customerRepository.save(TestUtils.sampleCustomerEntity2);
        roomRepository.save(sampleRoomEntity1);
        roomRepository.save(TestUtils.sampleRoomEntity2);
        sampleReservation1 = new ReservationEntity(sampleRoomEntity1, sampleCustomerEntity1, LocalDate.now(), 2);
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
        ReservationLogEntity log = new ReservationLogEntity(
                sampleReservation1, sampleReservation1.getState(), ReservationState.PAID
        );

        reservationLogRepository.save(log);

        assertEquals(1, reservationLogRepository.count());
        assertTrue(reservationLogRepository.findAll().contains(log));
    }
}
