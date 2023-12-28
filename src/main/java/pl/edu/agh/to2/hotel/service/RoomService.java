package pl.edu.agh.to2.hotel.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.to2.hotel.mapper.ModelEntityMapper;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.model.filters.RoomFilter;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final ModelEntityMapper modelEntityMapper;

    public RoomService(RoomRepository roomRepository, ModelEntityMapper modelEntityMapper) {
        this.roomRepository = roomRepository;
        this.modelEntityMapper = modelEntityMapper;
    }

    public Optional<Room> findRoomById(long id) {
        return roomRepository.findById(id).map(modelEntityMapper::mapRoomFromEntity);
    }

    public Optional<Room> getRoomByRoomNumberAndFloor(String roomNumber, int floor) {
        return roomRepository.findRoomByRoomNumberAndFloor(roomNumber, floor).map(modelEntityMapper::mapRoomFromEntity);
    }

    public List<Room> findRoomsWithRentPriceInRange(double min, double max) {
        return roomRepository.findRoomEntitiesByRentPriceBetween(min, max)
                .stream().map(modelEntityMapper::mapRoomFromEntity).toList();
    }

    public List<Room> findAvailableRoomsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return roomRepository.findAvailableRoomsBetweenDates(startDate, endDate)
                .stream().map(modelEntityMapper::mapRoomFromEntity).toList();
    }

    public boolean isRoomAvailableBetweenDates(Room room, LocalDate startDate, LocalDate endDate) {
        return roomRepository.isRoomAvailableBetweenDates(room.getId(), startDate, endDate);
    }

    public List<Room> searchRooms(RoomFilter filter) {
        return roomRepository.searchRooms(filter, PageRequest.of(0, 10)).stream().map(modelEntityMapper::mapRoomFromEntity).toList();
    }

    public Room createNewRoom(Room room) {
        throwIfThereAreUnfulfilledFields(room);
        throwExceptionIfRoomNumberIsDoubled(room);
        return saveRoom(room);
    }

    public Room updateRoom(Room room) {
        throwIfThereAreUnfulfilledFields(room);
        throwExceptionIfRoomNumberIsDoubled(room);
        return saveRoom(room);
    }

    private void throwExceptionIfRoomNumberIsDoubled(Room room) {
        // there should not be any two rooms with the same number and floor
        Optional<Room> found = getRoomByRoomNumberAndFloor(room.getRoomNumber(), room.getFloor());
        if (found.isPresent() && found.get().getId() != room.getId()) {
            throw new IllegalOperationException("The room with number %s on floor %d already exists!".formatted(room.getRoomNumber(), room.getFloor()));
        }
    }

    private void throwIfThereAreUnfulfilledFields(Room room) {
        if(room.getRoomNumber() == null || room.getRoomNumber().isBlank()) {
            throw new IllegalOperationException("The room number cannot be empty!");
        }
        if(room.getRoomStandard() == null) {
            throw new IllegalOperationException("The room standard cannot be empty!");
        }
        if(room.getBeds() == null || room.getBeds().isEmpty()) {
            throw new IllegalOperationException("The room beds specification have to contain at least one bed!");
        }
    }

    private Room saveRoom(Room room) {
        RoomEntity entity = roomRepository.save(modelEntityMapper.mapRoomToEntity(room));
        return modelEntityMapper.mapRoomFromEntity(entity);
    }
}