package pl.edu.agh.to2.hotel.mapper;

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
public class ModelEntityMapper {

    public Reservation mapReservationFromEntity(ReservationEntity reservationEntity) {
        Room room = this.mapRoomFromEntity(reservationEntity.getRoom());
        Customer customer = this.mapCustomerFromEntity(reservationEntity.getCustomer());
        return new Reservation(
                reservationEntity.getId(),
                room,
                customer,
                reservationEntity.getStartDate(),
                reservationEntity.getEndDate(),
                reservationEntity.getState());
    }

    public ReservationEntity mapReservationToEntity(Reservation reservation) {
        RoomEntity roomEntity = mapRoomToEntity(reservation.getRoom());
        CustomerEntity customerEntity = mapCustomerToEntity(reservation.getCustomer());
        return new ReservationEntity(
                reservation.getId(),
                roomEntity,
                customerEntity,
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getState());
    }

    public Room mapRoomFromEntity(RoomEntity roomEntity) {
		return new Room(
                roomEntity.getId(),
                roomEntity.getRoomNumber(),
                roomEntity.getFloor(),
                roomEntity.getBeds(),
                roomEntity.getRoomStandard(),
                roomEntity.getRentPrice());
    }

    public RoomEntity mapRoomToEntity(Room room) {
		return new RoomEntity(
                room.getId(),
                room.getRoomNumber(),
                room.getFloor(),
                room.getBeds(),
                room.getRoomStandard(),
                room.getRentPrice());
    }

    public Customer mapCustomerFromEntity(CustomerEntity customerEntity) {
        return new Customer(customerEntity.getId(),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getPhoneNumber(),
                customerEntity.getEmail());
    }

    public CustomerEntity mapCustomerToEntity(Customer customer) {
		return new CustomerEntity(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getEmail());
    }

    public Log mapLogFromEntity(ReservationLogEntity reservationLogEntity) {
        Reservation reservation = this.mapReservationFromEntity(reservationLogEntity.getReservation());
		return new Log(
                reservationLogEntity.getId(),
                reservationLogEntity.getDate(),
                reservation,
                reservationLogEntity.getUpdatedState());
    }

    public ReservationLogEntity mapLogToEntity(Log log) {
        ReservationEntity reservation = this.mapReservationToEntity(log.reservation());
        return new ReservationLogEntity(
                log.id(),
                log.date(),
                reservation,
                log.updatedState());
    }
}
