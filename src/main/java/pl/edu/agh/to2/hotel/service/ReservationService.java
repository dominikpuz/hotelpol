package pl.edu.agh.to2.hotel.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.hotel.mapper.ModelEntityMapper;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
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

    public ReservationService(ReservationRepository reservationRepository, ModelEntityMapper modelEntityMapper, ReservationLogService logService) {
        this.reservationRepository = reservationRepository;
        this.modelEntityMapper = modelEntityMapper;
        this.logService = logService;
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

    private Reservation saveReservation(Reservation reservation) {
        ReservationEntity entity = reservationRepository.save(modelEntityMapper.mapReservationToEntity(reservation));
        return modelEntityMapper.mapReservationFromEntity(entity);
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
        Reservation created = saveReservation(reservation);
        logService.saveReservationLog(created, ReservationState.CREATED);
        return created;
    }
}
