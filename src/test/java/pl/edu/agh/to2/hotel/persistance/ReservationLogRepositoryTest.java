package pl.edu.agh.to2.hotel.persistance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogEntity;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ReservationLogRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationLogRepository reservationLogRepository;
    private ReservationEntity sampleReservation1, sampleReservation2;

    @BeforeEach
    public void setUp() {
        reservationLogRepository.deleteAll();
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        customerRepository.deleteAll();

        CustomerEntity customer1 = TestUtils.createCustomerEntity1();
        CustomerEntity customer2 = TestUtils.createCustomerEntity2();
        RoomEntity room1 = TestUtils.createRoomEntity1();
        RoomEntity room2 = TestUtils.createRoomEntity2();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        roomRepository.save(room1);
        roomRepository.save(room2);
        sampleReservation1 = new ReservationEntity(room1, customer1, LocalDate.now(), 2);
        sampleReservation2 = new ReservationEntity(room2, customer2, LocalDate.now().plusDays(2), 3);
        reservationRepository.save(sampleReservation1);
        reservationRepository.save(sampleReservation2);
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
                sampleReservation1, ReservationState.PAID
        );

        reservationLogRepository.save(log);

        assertEquals(1, reservationLogRepository.count());
        assertTrue(reservationLogRepository.findAll().contains(log));
    }

    private ReservationLogEntity saveLogForNewReservation(ReservationEntity reservation) {
        ReservationLogEntity log = new ReservationLogEntity(reservation, ReservationState.CREATED);
        return reservationLogRepository.save(log);
    }

    private ReservationLogEntity saveLogForUpdate(ReservationEntity reservation, ReservationState updatedState) {
        ReservationLogEntity log = new ReservationLogEntity(reservation, updatedState);
        return reservationLogRepository.save(log);
    }

    @Test
    public void shouldFindAllLogsForReservation() {
        ReservationLogEntity log1 = saveLogForNewReservation(sampleReservation1);
        ReservationLogEntity log2 = saveLogForUpdate(sampleReservation1, ReservationState.PAID);
        saveLogForNewReservation(sampleReservation2);
        ReservationLogEntity log3 = saveLogForUpdate(sampleReservation1, ReservationState.CHECKED_IN);

        List<ReservationLogEntity> logs = reservationLogRepository.findAllByReservation_Id(sampleReservation1.getId());

        assertEquals(3, logs.size());
        assertTrue(logs.contains(log1));
        assertTrue(logs.contains(log2));
        assertTrue(logs.contains(log3));
    }
}
