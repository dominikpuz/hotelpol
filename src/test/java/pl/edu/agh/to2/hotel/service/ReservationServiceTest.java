package pl.edu.agh.to2.hotel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomService roomService;

    @SpyBean
    private ReservationRepository reservationRepository;

    @SpyBean
    private ReservationLogRepository logRepository;


    @SpyBean
    private ReservationLogService logService;
    @BeforeEach
    public void setUp() {
        reservationRepository.deleteAll();
        logRepository.deleteAll();
    }

    @Test
    public void shouldSaveReservationWithLog() {
        Customer customer = customerService.addNewCustomer( new Customer("Jan", "Kowalski", "123321123", "jankow@asd.dsa"));
        Room room = roomService.createNewRoom(new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0));
        Reservation notSaved = new Reservation(room, customer, LocalDate.now(), 2);

        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        ArgumentCaptor<ReservationState> stateCaptor = ArgumentCaptor.forClass(ReservationState.class);

        Reservation saved = reservationService.addNewReservation(notSaved);

        verify(reservationRepository, times(1)).save(any());

        verify(logService, times(1)).saveReservationLog(reservationCaptor.capture(), stateCaptor.capture());

        Reservation capturedReservation = reservationCaptor.getValue();
        ReservationState capturedState = stateCaptor.getValue();

        assertEquals(saved, capturedReservation);
        assertEquals(ReservationState.CREATED, capturedState);
    }

    @Test
    public void shouldUpdateReservationWithLog() {
        Customer customer = customerService.addNewCustomer( new Customer("Jan", "Kowalski", "123321123", "jankow@asd.dsa"));
        Room room = roomService.createNewRoom(new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0));
        Reservation notSaved = new Reservation(room, customer, LocalDate.now(), 2);

        Reservation reservation = reservationService.addNewReservation(notSaved);

        reservationService.updateReservationState(reservation, ReservationState.PAID);

        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        ArgumentCaptor<ReservationState> stateCaptor = ArgumentCaptor.forClass(ReservationState.class);

        verify(reservationRepository, times(2)).save(any());

        verify(logService, times(2)).saveReservationLog(reservationCaptor.capture(), stateCaptor.capture());

        Reservation capturedReservation = reservationCaptor.getAllValues().get(1);
        ReservationState capturedState = stateCaptor.getAllValues().get(1);

        assertEquals(reservation, capturedReservation);
        assertEquals(ReservationState.PAID, capturedState);
    }

    @Test
    public void shouldNotSaveIfRoomIsNull() {
        Customer customer = customerService.addNewCustomer( new Customer("Jan", "Kowalski", "123321123", "jankow@asd.dsa"));
        Reservation notSaved = new Reservation(null, customer, LocalDate.now(), 2);

        var exception = assertThrows(IllegalOperationException.class, () -> reservationService.addNewReservation(notSaved));
        assertEquals("Room is not chosen properly!", exception.getMessage());
    }

    @Test
    public void shouldNotSaveIfRoomIsNotSaved() {
        Customer customer = customerService.addNewCustomer( new Customer("Jan", "Kowalski", "123321123", "jankow@asd.dsa"));
        Room room = new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0);
        Reservation notSaved = new Reservation(room, customer, LocalDate.now(), 2);

        var exception = assertThrows(IllegalOperationException.class, () -> reservationService.addNewReservation(notSaved));
        assertEquals("Room is not chosen properly!", exception.getMessage());
    }

    @Test
    public void shouldNotSaveIfCustomerIsNull() {
        Room room = roomService.createNewRoom(new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0));
        Reservation notSaved = new Reservation(room, null, LocalDate.now(), 2);

        var exception = assertThrows(IllegalOperationException.class, () -> reservationService.addNewReservation(notSaved));
        assertEquals("Customer is not chosen properly!", exception.getMessage());
    }

    @Test
    public void shouldNotSaveIfCustomerIsNotSaved() {
        Customer customer = new Customer("Jan", "Kowalski", "123321123", "jankow@asd.dsa");
        Room room = roomService.createNewRoom(new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0));
        Reservation notSaved = new Reservation(room, customer, LocalDate.now(), 2);

        var exception = assertThrows(IllegalOperationException.class, () -> reservationService.addNewReservation(notSaved));
        assertEquals("Customer is not chosen properly!", exception.getMessage());
    }

    @Test
    public void shouldNotSaveIfEndDateIsBeforeStartDate() {
        Customer customer = customerService.addNewCustomer( new Customer("Jan", "Kowalski", "123321123", "jankow@asd.dsa"));
        Room room = roomService.createNewRoom(new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0));
        Reservation notSaved = new Reservation(room, customer, LocalDate.now(), LocalDate.now().minusDays(2), ReservationState.CREATED);

        var exception = assertThrows(IllegalOperationException.class, () -> reservationService.addNewReservation(notSaved));
        assertEquals("End date have to be after start date!", exception.getMessage());
    }
}
