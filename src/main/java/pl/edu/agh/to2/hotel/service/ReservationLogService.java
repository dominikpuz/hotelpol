package pl.edu.agh.to2.hotel.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.hotel.mapper.ModelEntityMapper;
import pl.edu.agh.to2.hotel.model.Log;
import pl.edu.agh.to2.hotel.model.Reservation;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogEntity;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationLogService {
    private final ReservationLogRepository reservationLogRepository;

    private final ModelEntityMapper modelEntityMapper;

    public ReservationLogService(ReservationLogRepository reservationLogRepository, ModelEntityMapper modelEntityMapper) {
        this.reservationLogRepository = reservationLogRepository;
        this.modelEntityMapper = modelEntityMapper;
    }

    public List<Log> findAllLogsForReservation(long reservationId) {
        return reservationLogRepository.findAllByReservation_Id(reservationId)
                .stream().map(modelEntityMapper::mapLogFromEntity).toList();
    }

    public Log saveReservationLog(Reservation reservation, ReservationState updatedState) {
        return saveLog(new Log(LocalDateTime.now(), reservation, updatedState));
    }

    private Log saveLog(Log log) {
        ReservationLogEntity entity = reservationLogRepository.save(modelEntityMapper.mapLogToEntity(log));
        return modelEntityMapper.mapLogFromEntity(entity);
    }
}