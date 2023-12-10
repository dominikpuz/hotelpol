package pl.edu.agh.to2.hotel.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.to2.hotel.mapper.ModelEntityMapper;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomFilter;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

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

    public List<Room> searchRooms(RoomFilter filter) {
        return roomRepository.searchRooms(filter, PageRequest.of(0, 10)).stream().map(modelEntityMapper::mapRoomFromEntity).toList();
    }

    public Room createNewRoom(Room room) {
        return saveRoom(room);
    }

    private Room saveRoom(Room room) {
        RoomEntity entity = roomRepository.save(modelEntityMapper.mapRoomToEntity(room));
        return modelEntityMapper.mapRoomFromEntity(entity);
    }
}