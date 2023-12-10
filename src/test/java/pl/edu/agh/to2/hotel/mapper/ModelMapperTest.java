package pl.edu.agh.to2.hotel.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.time.LocalDate;
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

    @BeforeEach
    public void init() {
        sampleRoomEntity = TestUtils.createRoomEntity1();
        sampleRoom =  new Room(1, "305A", 3,Arrays.asList(SINGLE_BED, DOUBLE_BED), RoomStandard.EXCLUSIVE, 200.0);

        sampleCustomerEntity = TestUtils.createCustomerEntity1();
        sampleCustomer = new Customer(1, "Jan", "Kowalski", "123456789", "jkowlaski@abc.com");

        sampleReservationEntity = new ReservationEntity(sampleRoomEntity, sampleCustomerEntity, LocalDate.now(), LocalDate.now().plusDays(2), ReservationState.CREATED);
        sampleReservation = new Reservation(1, sampleRoom, sampleCustomer, LocalDate.now(), LocalDate.now().plusDays(3), ReservationState.CREATED);
    }

    @Test
    public void customerEntityToCustomer() {
        Customer customer = modelMapper.map(sampleCustomerEntity, Customer.class);

        assertCustomerEntityAndCustomerAreEqual(sampleCustomerEntity, customer);
    }

    @Test
    public void customerToCustomerEntity() {
        CustomerEntity customerEntity = modelMapper.map(sampleCustomer, CustomerEntity.class);

        assertCustomerEntityAndCustomerAreEqual(customerEntity, sampleCustomer);
    }

    @Test
    public void roomEntityToRoom() {
        Room room = modelMapper.map(sampleRoomEntity, Room.class);

        assertRoomEntityAndRoomAreEqual(sampleRoomEntity, room);
    }

    @Test
    public void roomToRoomEntity() {
        RoomEntity roomEntity = modelMapper.map(sampleRoom, RoomEntity.class);

        assertRoomEntityAndRoomAreEqual(roomEntity, sampleRoom);
    }

    @Test
    public void reservationEntityToReservation() {
        Reservation reservation = modelMapper.map(sampleReservationEntity, Reservation.class);

        assertEquals(sampleReservationEntity.getId(), reservation.getId());
        assertRoomEntityAndRoomAreEqual(sampleReservationEntity.getRoom(), reservation.getRoom());
        assertCustomerEntityAndCustomerAreEqual(sampleReservationEntity.getCustomer(), reservation.getCustomer());
        assertEquals(sampleReservationEntity.getStartDate(), reservation.getStartDate());
        assertEquals(sampleReservationEntity.getEndDate(), reservation.getEndDate());
        assertEquals(sampleReservationEntity.getState(), reservation.getState());
    }

    @Test
    public void reservationToReservationEntity() {
        ReservationEntity reservationEntity = modelMapper.map(sampleReservation, ReservationEntity.class);

        assertEquals(reservationEntity.getId(), sampleReservation.getId());
        assertRoomEntityAndRoomAreEqual(reservationEntity.getRoom(), sampleReservation.getRoom());
        assertCustomerEntityAndCustomerAreEqual(reservationEntity.getCustomer(), sampleReservation.getCustomer());
        assertEquals(reservationEntity.getStartDate(), sampleReservation.getStartDate());
        assertEquals(reservationEntity.getEndDate(), sampleReservation.getEndDate());
        assertEquals(reservationEntity.getState(), sampleReservation.getState());
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
}
