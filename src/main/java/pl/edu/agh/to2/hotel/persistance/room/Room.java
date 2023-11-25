package pl.edu.agh.to2.hotel.persistance.room;

import jakarta.persistence.*;
import pl.edu.agh.to2.hotel.persistance.StringBedTypeListConverter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ROOMS")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private long id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "beds", nullable = false)
    @Convert(converter = StringBedTypeListConverter.class)
    private List<BedType> beds;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_standard", nullable = false)
    private RoomStandard roomStandard;

    @Column(name = "rent_price", nullable = false)
    private double rentPrice;

    public Room(String roomNumber, int floor, List<BedType> beds, RoomStandard roomStandard, double rentPrice) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.beds = beds;
        this.roomStandard = roomStandard;
        this.rentPrice = rentPrice;
    }

    public Room() {
    }

    public long getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public List<BedType> getBeds() {
        return beds;
    }

    public RoomStandard getRoomStandard() {
        return roomStandard;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setBeds(List<BedType> beds) {
        this.beds = beds;
    }

    public void setRoomStandard(RoomStandard roomStandard) {
        this.roomStandard = roomStandard;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", floor=" + floor +
                ", beds=" + beds +
                ", roomStandard=" + roomStandard +
                ", rentPrice=" + rentPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return floor == room.floor && Double.compare(room.rentPrice, rentPrice) == 0 && Objects.equals(roomNumber, room.roomNumber) && Objects.equals(beds, room.beds) && roomStandard == room.roomStandard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, floor, beds, roomStandard, rentPrice);
    }
}
