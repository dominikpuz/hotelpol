package pl.edu.agh.to2.hotel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @SpyBean
    private RoomRepository roomRepository;

    @BeforeEach
    public void setUp() {
        roomRepository.deleteAll();
    }

    @Test
    public void shouldSaveNewRoomAndGenerateId() {
        Room notSaved = new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0);
        Room room = roomService.createNewRoom(notSaved);

        assertEquals(notSaved.getRoomStandard(), room.getRoomStandard());
        assertEquals(notSaved.getRoomNumber(), room.getRoomNumber());
        assertEquals(notSaved.getFloor(), room.getFloor());
        assertEquals(notSaved.getRentPrice(), room.getRentPrice());
        assertEquals(notSaved.getBeds(), room.getBeds());
        assertEquals(1, room.getId());

        verify(roomRepository, times(1)).save(any());
    }

    @Test
    public void shouldFindCustomerById() {
        Room notSaved = new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0);
        Room saved = roomService.createNewRoom(notSaved);

        Optional<Room> found = roomService.findRoomById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getRoomStandard(), found.get().getRoomStandard());
        assertEquals(saved.getRoomNumber(), found.get().getRoomNumber());
        assertEquals(saved.getFloor(), found.get().getFloor());
        assertEquals(saved.getRentPrice(), found.get().getRentPrice());
        assertEquals(saved.getBeds(), found.get().getBeds());
    }

    @ParameterizedTest
    @MethodSource("validationArgumentsProvider")
    public void shouldNotAddNewRoomDueToValidation(Room room, String validationMessage) {
        var exception = assertThrows(IllegalOperationException.class, () -> roomService.createNewRoom(room));
        assertEquals(validationMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validationArgumentsProvider")
    public void shouldNotEditRoomDueToValidation(Room room, String validationMessage) {
        roomService.createNewRoom(new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0));

        var exception = assertThrows(IllegalOperationException.class, () -> roomService.updateRoom(room));
        assertEquals(validationMessage, exception.getMessage());
    }

    @Test
    public void shouldNotAddNewRoomIfRoomIsDuplicated() {
        Room room1 = new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0);
        Room room2 = new Room("123A", 1, List.of(BedType.SINGLE_BED), RoomStandard.EXCLUSIVE, 321.0);

        roomService.createNewRoom(room1);

        var exception = assertThrows(IllegalOperationException.class, () -> roomService.createNewRoom(room2));
        assertEquals("The room with number 123A on floor 1 already exists!", exception.getMessage());
    }

    @Test
    public void shouldNotUpdateRoomIfRoomIsDuplicated() {
        Room room1 = new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0);
        Room room2 = new Room("321A", 1, List.of(BedType.SINGLE_BED), RoomStandard.EXCLUSIVE, 321.0);
        Room room2Updated = new Room("123A", 1, List.of(BedType.SINGLE_BED), RoomStandard.EXCLUSIVE, 321.0);

        roomService.createNewRoom(room1);
        roomService.createNewRoom(room2);

        var exception = assertThrows(IllegalOperationException.class, () -> roomService.updateRoom(room2Updated));
        assertEquals("The room with number 123A on floor 1 already exists!", exception.getMessage());
    }

    public static Stream<Arguments> validationArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new Room(null, 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0),
                        "The room number cannot be empty!"
                ),
                Arguments.of(
                        new Room(" ", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.CLASSIC, 123.0),
                        "The room number cannot be empty!"
                ),
                Arguments.of(
                        new Room("123A", 1, null, RoomStandard.CLASSIC, 123.0),
                        "The room beds specification have to contain at least one bed!"
                ),
                Arguments.of(
                        new Room("123A", 1, List.of(), RoomStandard.CLASSIC, 123.0),
                        "The room beds specification have to contain at least one bed!"
                ),
                Arguments.of(
                        new Room("123A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), null, 123.0),
                        "The room standard cannot be empty!"
                )
        );
    }
}
