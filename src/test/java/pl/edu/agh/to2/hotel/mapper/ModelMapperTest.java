package pl.edu.agh.to2.hotel.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Log;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.agh.to2.hotel.persistance.room.BedType.DOUBLE_BED;
import static pl.edu.agh.to2.hotel.persistance.room.BedType.SINGLE_BED;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModelMapperTest {
    @Autowired
    private ModelEntityMapper modelMapper;

    private RoomEntity sampleRoomEntity;
    private Room sampleRoom;
    private CustomerEntity sampleCustomerEntity;
    private Customer sampleCustomer;
    private ReservationEntity sampleReservationEntity;
    private Reservation sampleReservation;
    private ReservationLogEntity sampleReservationLogEntity;
    private Log sampleLog;

    @BeforeEach
    public void init() {
        sampleRoomEntity = TestUtils.createRoomEntity1();
        sampleRoom =  new Room(1, "305A", 3,Arrays.asList(SINGLE_BED, DOUBLE_BED), RoomStandard.EXCLUSIVE, 200.0);

        sampleCustomerEntity = TestUtils.createCustomerEntity1();
        sampleCustomer = new Customer(1, "Jan", "Kowalski", "123456789", "jkowlaski@abc.com");

        sampleReservationEntity = new ReservationEntity(sampleRoomEntity, sampleCustomerEntity, LocalDate.now(), LocalDate.now().plusDays(2), ReservationState.CREATED);
        sampleReservation = new Reservation(1, sampleRoom, sampleCustomer, LocalDate.now(), LocalDate.now().plusDays(3), ReservationState.CREATED);

        LocalDateTime logDate = LocalDateTime.now();
        sampleReservationLogEntity = new ReservationLogEntity(1, logDate, sampleReservationEntity, ReservationState.PAID);
        sampleLog = new Log(1, logDate, sampleReservation, ReservationState.PAID);
    }

    @Test
    public void customerEntityToCustomer() {
        Customer customer = modelMapper.mapCustomerFromEntity(sampleCustomerEntity);

        assertCustomerEntityAndCustomerAreEqual(sampleCustomerEntity, customer);
    }

    @Test
    public void customerToCustomerEntity() {
        CustomerEntity customerEntity = modelMapper.mapCustomerToEntity(sampleCustomer);

        assertCustomerEntityAndCustomerAreEqual(customerEntity, sampleCustomer);
    }

    @Test
    public void roomEntityToRoom() {
        Room room = modelMapper.mapRoomFromEntity(sampleRoomEntity);

        assertRoomEntityAndRoomAreEqual(sampleRoomEntity, room);
    }

    @Test
    public void roomToRoomEntity() {
        RoomEntity roomEntity = modelMapper.mapRoomToEntity(sampleRoom);

        assertRoomEntityAndRoomAreEqual(roomEntity, sampleRoom);
    }

    @Test
    public void reservationEntityToReservation() {
        Reservation reservation = modelMapper.mapReservationFromEntity(sampleReservationEntity);

        assertReservationEntityAndReservationAreEqual(sampleReservationEntity, reservation);
    }

    @Test
    public void reservationToReservationEntity() {
        ReservationEntity reservationEntity = modelMapper.mapReservationToEntity(sampleReservation);

        assertReservationEntityAndReservationAreEqual(reservationEntity, sampleReservation);
    }

    @Test
    public void reservationLogEntityToLog() {
        Log log = modelMapper.mapLogFromEntity(sampleReservationLogEntity);

        assertReservationLogEntityAndLogAreEqual(sampleReservationLogEntity, log);
    }

    @Test
    public void logToReservationLogEntity() {
        ReservationLogEntity reservationLogEntity = modelMapper.mapLogToEntity(sampleLog);

        assertReservationLogEntityAndLogAreEqual(reservationLogEntity, sampleLog);
    }
    private void assertRoomEntityAndRoomAreEqual(RoomEntity roomEntity, Room room) {
        assertEquals(roomEntity.getId(), room.getId());
        assertEquals(roomEntity.getRoomNumber(), room.getRoomNumber());
        assertEquals(roomEntity.getFloor(), room.getFloor());
        assertEquals(roomEntity.getRoomStandard(), room.getRoomStandard());
        assertEquals(roomEntity.getBeds(), room.getBeds());
        assertEquals(roomEntity.getRentPrice(), room.getRentPrice());
    }

    private void assertCustomerEntityAndCustomerAreEqual(CustomerEntity customerEntity, Customer customer) {
        assertEquals(customerEntity.getId(), customer.getId());
        assertEquals(customerEntity.getFirstName(), customer.getFirstName());
        assertEquals(customerEntity.getLastName(), customer.getLastName());
        assertEquals(customerEntity.getPhoneNumber(), customer.getPhoneNumber());
        assertEquals(customerEntity.getEmail(), customer.getEmail());
    }

    private void assertReservationEntityAndReservationAreEqual(ReservationEntity reservationEntity, Reservation reservation) {
        assertEquals(reservationEntity.getId(), reservation.getId());
        assertRoomEntityAndRoomAreEqual(reservationEntity.getRoom(), reservation.getRoom());
        assertCustomerEntityAndCustomerAreEqual(reservationEntity.getCustomer(), reservation.getCustomer());
        assertEquals(reservationEntity.getStartDate(), reservation.getStartDate());
        assertEquals(reservationEntity.getEndDate(), reservation.getEndDate());
        assertEquals(reservationEntity.getState(), reservation.getState());
    }

    private void assertReservationLogEntityAndLogAreEqual(ReservationLogEntity logEntity, Log log) {
        assertEquals(logEntity.getId(), log.id());
        assertReservationEntityAndReservationAreEqual(logEntity.getReservation(), log.reservation());
        assertEquals(logEntity.getDate(), log.date());
        assertEquals(logEntity.getUpdatedState(), log.updatedState());
    }
}
