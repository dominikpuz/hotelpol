package pl.edu.agh.to2.hotel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Log;
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


    @Autowired
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

        Reservation saved = reservationService.addNewReservation(notSaved);

        verify(reservationRepository, times(1)).save(any());

        List<Log> logs = logService.findAllLogsForReservation(saved.getId());

        assertEquals(1, logs.size());
        Log log = logs.get(0);
        assertEquals(ReservationState.CREATED, log.updatedState());
        assertEquals(saved.getId(), log.reservation().getId());
    }
}
