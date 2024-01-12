package pl.edu.agh.to2.hotel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.to2.hotel.mapper.ModelEntityMapper;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.model.filters.ReservationFilter;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private final ModelEntityMapper modelEntityMapper;

    private final ReservationLogService logService;

    private final RoomService roomService;

    public ReservationService(ReservationRepository reservationRepository, ModelEntityMapper modelEntityMapper, ReservationLogService logService, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.modelEntityMapper = modelEntityMapper;
        this.logService = logService;
        this.roomService = roomService;
    }

    public Optional<Reservation> findReservationById(long id) {
        return reservationRepository.findById(id).map(modelEntityMapper::mapReservationFromEntity);
    }

    public List<Reservation> findReservationsByEndDate(LocalDate date) {
        return reservationRepository.findReservationsByEndDateEquals(date)
                .stream().map(modelEntityMapper::mapReservationFromEntity).toList();
    }

    public List<Reservation> findReservationsByStartDate(LocalDate date) {
        return reservationRepository.findReservationsByStartDateEquals(date)
                .stream().map(modelEntityMapper::mapReservationFromEntity).toList();
    }

    public Page<Reservation> searchReservations(ReservationFilter filter, int page) {
        Page<ReservationEntity> reservationEntities = reservationRepository.searchReservations(filter, PageRequest.of(page, 14));
        return reservationEntities.map(modelEntityMapper::mapReservationFromEntity);
    }

    public Reservation updateReservationState(Reservation reservation, ReservationState updatedState) {
        if(!reservation.getState().canChangeTo(updatedState)) {
            throw new IllegalOperationException("Reservation with state %s cannot be updated to %s".formatted(reservation.getState(), updatedState));
        }
        reservation.setState(updatedState);
        logService.saveReservationLog(reservation, updatedState);
        return saveReservation(reservation);
    }

    public Reservation addNewReservation(Reservation reservation) {
        throwIfUnfulfilledRoomOrCustomer(reservation);
        throwIfInvalidReservationDates(reservation);
        throwIfRoomIsUnavailable(reservation);
        Reservation created = saveReservation(reservation);
        logService.saveReservationLog(created, ReservationState.CREATED);
        return created;
    }

    public Reservation updateReservation(Reservation reservation) {
        throwIfUnfulfilledRoomOrCustomer(reservation);
        throwIfInvalidReservationDates(reservation);
        throwIfRoomIsUnavailable(reservation);
        return saveReservation(reservation);
    }

    private void throwIfRoomIsUnavailable(Reservation reservation) {
        if(!roomService.isRoomAvailableBetweenDates(reservation.getRoom(), reservation.getStartDate(), reservation.getEndDate())) {
            throw new IllegalOperationException("Room %s is not available in given period!".formatted(reservation.getRoom().getRoomNumber()));
        }
    }

    private void throwIfUnfulfilledRoomOrCustomer(Reservation reservation) {
        if(reservation.getRoom() == null || reservation.getRoom().getId() == -1) {
            throw new IllegalOperationException("Room is not chosen properly!");
        }
        if(reservation.getCustomer() == null || reservation.getCustomer().getId() == -1) {
            throw new IllegalOperationException("Customer is not chosen properly!");
        }
    }

    private void throwIfInvalidReservationDates(Reservation reservation) {
        if(reservation.getStartDate() == null || reservation.getEndDate() == null) {
            throw new IllegalOperationException("Start date and end date of reservation have to be non-null!");
        }
        if(reservation.getEndDate().isBefore(reservation.getStartDate())) {
            throw new IllegalOperationException("End date have to be after start date!");
        }
    }

    private Reservation saveReservation(Reservation reservation) {
        ReservationEntity entity = reservationRepository.save(modelEntityMapper.mapReservationToEntity(reservation));
        return modelEntityMapper.mapReservationFromEntity(entity);
    }
}
