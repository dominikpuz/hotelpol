package pl.edu.agh.to2.hotel.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;

@Component
public class ModelEntityMapper extends ModelMapper {
    public ModelEntityMapper() {
        Converter<CustomerEntity, Customer> customerConverter = context -> {
            CustomerEntity customerEntity = context.getSource();
            return new Customer(customerEntity.getId(), customerEntity.getFirstName(),
                    customerEntity.getLastName(), customerEntity.getPhoneNumber(), customerEntity.getEmail());
        };
        this.createTypeMap(CustomerEntity.class, Customer.class).setConverter(customerConverter);

        Converter<RoomEntity, Room> roomConverter = context -> {
          RoomEntity roomEntity = context.getSource();
          return new Room(roomEntity.getId(), roomEntity.getRoomNumber(), roomEntity.getFloor(), roomEntity.getBeds(),
                  roomEntity.getRoomStandard(), roomEntity.getRentPrice());
        };
        this.createTypeMap(RoomEntity.class, Room.class).setConverter(roomConverter);

        Converter<ReservationEntity, Reservation> reservationConverter = context -> {
          ReservationEntity reservationEntity = context.getSource();
          Room room = this.map(reservationEntity.getRoom(), Room.class);
          Customer customer = this.map(reservationEntity.getCustomer(), Customer.class);
          return new Reservation(reservationEntity.getId(), room, customer, reservationEntity.getStartDate(),
                  reservationEntity.getEndDate(), reservationEntity.getState());
        };
        this.createTypeMap(ReservationEntity.class, Reservation.class).setConverter(reservationConverter);
    }
}
