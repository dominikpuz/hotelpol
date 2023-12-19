package pl.edu.agh.to2.hotel.model;

import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.util.List;

@Getter
@Setter
public class Room implements IPresentableModel {
    private final long id;
    private String roomNumber;
    private int floor;
    private List<BedType> beds;
    private RoomStandard roomStandard;
    private double rentPrice;

    public Room(String roomNumber, int floor, List<BedType> beds, RoomStandard roomStandard, double rentPrice) {
        this(-1, roomNumber, floor, beds, roomStandard, rentPrice);
    }

    public Room(long id, String roomNumber, int floor, List<BedType> beds, RoomStandard roomStandard, double rentPrice) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.beds = beds;
        this.roomStandard = roomStandard;
        this.rentPrice = rentPrice;
    }

    public Room() {
        id = -1;
    }
}