package pl.edu.agh.to2.hotel.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.Log;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;

@Component
public class ModelEntityMapper extends ModelMapper {
    public ModelEntityMapper() {
        // Prepare the mapper by configuring it

        // CustomerEntity to Customer mapping
        Converter<CustomerEntity, Customer> customerConverter = context -> {
            CustomerEntity customerEntity = context.getSource();
            return new Customer(customerEntity.getId(), customerEntity.getFirstName(),
                    customerEntity.getLastName(), customerEntity.getPhoneNumber(), customerEntity.getEmail());
        };
        this.createTypeMap(CustomerEntity.class, Customer.class).setConverter(customerConverter);

        // RoomEntity to Room mapping
        Converter<RoomEntity, Room> roomConverter = context -> {
          RoomEntity roomEntity = context.getSource();
          return new Room(roomEntity.getId(), roomEntity.getRoomNumber(), roomEntity.getFloor(), roomEntity.getBeds(),
                  roomEntity.getRoomStandard(), roomEntity.getRentPrice());
        };
        this.createTypeMap(RoomEntity.class, Room.class).setConverter(roomConverter);

        // ReservationEntity to Reservation mapping
        Converter<ReservationEntity, Reservation> reservationConverter = context -> {
          ReservationEntity reservationEntity = context.getSource();
          Room room = this.map(reservationEntity.getRoom(), Room.class);
          Customer customer = this.map(reservationEntity.getCustomer(), Customer.class);
          return new Reservation(reservationEntity.getId(), room, customer, reservationEntity.getStartDate(),
                  reservationEntity.getEndDate(), reservationEntity.getState());
        };
        this.createTypeMap(ReservationEntity.class, Reservation.class).setConverter(reservationConverter);

        Converter<ReservationLogEntity, Log> logConverter = context -> {
            ReservationLogEntity reservationLogEntity = context.getSource();
            Reservation reservation = this.map(reservationLogEntity.getReservation(), Reservation.class);
            return new Log(reservationLogEntity.getDate(), reservation, reservationLogEntity.getUpdatedState());
        };
        this.createTypeMap(ReservationLogEntity.class, Log.class).setConverter(logConverter);

        Converter<Log, ReservationLogEntity> logConverterReversed = context -> {
            Log log = context.getSource();
            ReservationEntity reservation = this.map(log.reservation(), ReservationEntity.class);
            return new ReservationLogEntity(log.date(), reservation, log.updatedState());
        };

        this.createTypeMap(Log.class, ReservationLogEntity.class).setConverter(logConverterReversed);
    }

    public Reservation mapReservationFromEntity(ReservationEntity entity) {
        return this.map(entity, Reservation.class);
    }

    public ReservationEntity mapReservationToEntity(Reservation reservation) {
        return this.map(reservation, ReservationEntity.class);
    }

    public Room mapRoomFromEntity(RoomEntity entity) {
        return this.map(entity, Room.class);
    }

    public RoomEntity mapRoomToEntity(Room room) {
        return this.map(room, RoomEntity.class);
    }

    public Customer mapCustomerFromEntity(CustomerEntity entity) {
        return this.map(entity, Customer.class);
    }

    public CustomerEntity mapCustomerToEntity(Customer customer) {
        return this.map(customer, CustomerEntity.class);
    }

    public Log mapLogFromEntity(ReservationLogEntity entity) {
        return this.map(entity, Log.class);
    }

    public ReservationLogEntity mapLogToEntity(Log log) {
        return this.map(log, ReservationLogEntity.class);
    }
}
