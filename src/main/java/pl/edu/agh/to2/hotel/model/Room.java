package pl.edu.agh.to2.hotel.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Getter;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.util.List;

@Getter
public class Room {
    private final long id;
    private final StringProperty roomNumber;
    private final IntegerProperty floor;
    private final ListProperty<BedType> beds;
    private final ObjectProperty<RoomStandard> roomStandard;
    private final DoubleProperty rentPrice;

    public Room() {
        id = -1;
        roomNumber = new SimpleStringProperty();
        floor = new SimpleIntegerProperty();
        beds = new SimpleListProperty<>();
        roomStandard = new SimpleObjectProperty<>();
        rentPrice = new SimpleDoubleProperty();
    }

    public Room(String roomNumber, int floor, List<BedType> beds, RoomStandard roomStandard, double rentPrice) {
        this(-1, roomNumber, floor, beds, roomStandard, rentPrice);
    }

    public Room(long id, String roomNumber, int floor, List<BedType> beds, RoomStandard roomStandard, double rentPrice) {
        this.id = id;
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.floor = new SimpleIntegerProperty(floor);
        this.beds = new SimpleListProperty<>(FXCollections.observableArrayList(beds));
        this.roomStandard = new SimpleObjectProperty<>(roomStandard);
        this.rentPrice = new SimpleDoubleProperty(rentPrice);
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public int getFloor() {
        return floor.get();
    }

    public void setFloor(int floor) {
        this.floor.set(floor);
    }

    public List<BedType> getBeds() {
        return beds.get();
    }

    public void setBeds(List<BedType> beds) {
        this.beds.set(FXCollections.observableArrayList(beds));
    }

    public RoomStandard getRoomStandard() {
        return roomStandard.get();
    }

    public void setRoomStandard(RoomStandard roomStandard) {
        this.roomStandard.set(roomStandard);
    }

    public double getRentPrice() {
        return rentPrice.get();
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice.set(rentPrice);
    }
}