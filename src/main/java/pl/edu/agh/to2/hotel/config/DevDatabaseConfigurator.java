package pl.edu.agh.to2.hotel.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogEntity;
import pl.edu.agh.to2.hotel.persistance.log.ReservationLogRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationState;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class used for development purposes. It fills the database with sample data
 */
@Configuration
@Profile("dev")
public class DevDatabaseConfigurator {
    @Bean
    CommandLineRunner commandLineRunner(
            RoomRepository roomRepository,
            CustomerRepository customerRepository,
            ReservationRepository reservationRepository,
            ReservationLogRepository logRepository)
    {
        return args -> {
            if (roomRepository.count() == 0) {
                var rooms = List.of(
                        new RoomEntity("104A", 1, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED, BedType.KID_BED), RoomStandard.FAMILY, 200.0),
                        new RoomEntity("104B", 1, List.of(BedType.DOUBLE_BED, BedType.DOUBLE_BED, BedType.KID_BED, BedType.KID_BED), RoomStandard.FAMILY, 250.0),
                        new RoomEntity("107B", 1, List.of(BedType.DOUBLE_BED), RoomStandard.CLASSIC, 145.0),
                        new RoomEntity("202A", 2, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.EXCLUSIVE, 200.0),
                        new RoomEntity("305", 3, List.of(BedType.SINGLE_BED, BedType.SINGLE_BED), RoomStandard.CLASSIC, 130.0),
                        new RoomEntity("301", 3, List.of(BedType.DOUBLE_BED, BedType.DOUBLE_BED), RoomStandard.EXCLUSIVE, 230.0),
                        new RoomEntity("201A", 2, List.of(BedType.SINGLE_BED, BedType.SINGLE_BED), RoomStandard.CLASSIC, 180.0),
                        new RoomEntity("402", 4, List.of(BedType.DOUBLE_BED, BedType.DOUBLE_BED, BedType.KID_BED), RoomStandard.FAMILY, 300.0),
                        new RoomEntity("503", 5, List.of(BedType.SINGLE_BED, BedType.DOUBLE_BED, BedType.SINGLE_BED), RoomStandard.EXCLUSIVE, 250.0)
                );
                roomRepository.saveAll(rooms);

                var customers = List.of(
                        new CustomerEntity("Jan", "Kowalski", "123321345", "jankowalski@gmail.com"),
                        new CustomerEntity("Natalia", "Wrona", "+48654982032", "natalia123@wp.pl"),
                        new CustomerEntity("Anna", "Nowak", "+48501345678", "annanowak@gmail.com"),
                        new CustomerEntity("Piotr", "Lis", "987654321", "piotrlis@wp.pl"),
                        new CustomerEntity("Maria", "Szymańska", "554433221", "mariaszymanska@gmail.com"),
                        new CustomerEntity("Tomasz", "Kowalczyk", "+48777223311", "tomaszkowalczyk@wp.pl"),
                        new CustomerEntity("Karolina", "Jankowska", "+48666666666", "karolinajankowska@gmail.com"),
                        new CustomerEntity("Marcin", "Lewandowski", "999888777", "marcinlewandowski@wp.pl")
                );
                customerRepository.saveAll(customers);

                var reservations = List.of(
                        new ReservationEntity(rooms.get(0), customers.get(0), LocalDate.of(2023, 11, 25), LocalDate.of(2023, 11, 30), ReservationState.CHECKED_OUT),
                        new ReservationEntity(rooms.get(0), customers.get(1), LocalDate.of(2023, 11, 20), LocalDate.of(2023, 11, 27), ReservationState.CHECKED_OUT),
                        new ReservationEntity(rooms.get(1), customers.get(1), LocalDate.of(2023, 12, 5), LocalDate.of(2023, 12, 10), ReservationState.PAID),
                        new ReservationEntity(rooms.get(3), customers.get(3), LocalDate.of(2023, 12, 15), LocalDate.of(2023, 12, 20)),
                        new ReservationEntity(rooms.get(4), customers.get(4), LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 5), ReservationState.CANCELLED),
                        new ReservationEntity(rooms.get(5), customers.get(5), LocalDate.of(2023, 12, 8), LocalDate.of(2023, 12, 12), ReservationState.PAID),
                        new ReservationEntity(rooms.get(6), customers.get(6), LocalDate.of(2023, 12, 18), LocalDate.of(2023, 12, 23)),
                        new ReservationEntity(rooms.get(7), customers.get(7), LocalDate.of(2023, 12, 10), LocalDate.of(2023, 12, 15))
                );
                reservationRepository.saveAll(reservations);

                var logs = List.of(
                        new ReservationLogEntity(LocalDateTime.of(2023, 11, 14, 14, 58, 0), reservations.get(0), ReservationState.CREATED),
                        new ReservationLogEntity(LocalDateTime.of(2023, 11, 14, 15, 34, 33), reservations.get(0), ReservationState.PAID),
                        new ReservationLogEntity(LocalDateTime.of(2023, 11, 25, 10, 23, 33), reservations.get(0), ReservationState.CHECKED_IN),
                        new ReservationLogEntity(LocalDateTime.of(2023, 11, 30, 7, 56, 9), reservations.get(0), ReservationState.CHECKED_OUT),

                        new ReservationLogEntity(LocalDateTime.of(2023, 11, 18, 20, 35, 2), reservations.get(1), ReservationState.CREATED),
                        new ReservationLogEntity(LocalDateTime.of(2023, 11, 18, 20, 41, 45), reservations.get(1), ReservationState.PAID),
                        new ReservationLogEntity(LocalDateTime.of(2023, 11, 19, 23, 45, 23), reservations.get(1), ReservationState.CHECKED_IN),
                        new ReservationLogEntity(LocalDateTime.of(2023, 11, 27, 8, 51, 48), reservations.get(1), ReservationState.CHECKED_OUT),

                        new ReservationLogEntity(LocalDateTime.of(2023, 10, 12, 12, 37, 25), reservations.get(2), ReservationState.CREATED),
                        new ReservationLogEntity(LocalDateTime.of(2023, 10, 12, 15, 23, 18), reservations.get(2), ReservationState.PAID),

                        new ReservationLogEntity(LocalDateTime.of(2023, 8, 12, 2, 2, 44), reservations.get(3), ReservationState.CREATED),

                        new ReservationLogEntity(LocalDateTime.of(2023, 2, 15, 16, 41, 5), reservations.get(4), ReservationState.CREATED),
                        new ReservationLogEntity(LocalDateTime.of(2023, 2, 16, 10, 15, 14), reservations.get(4), ReservationState.CANCELLED),

                        new ReservationLogEntity(LocalDateTime.of(2023, 6, 4, 21, 40, 5), reservations.get(5), ReservationState.CREATED),
                        new ReservationLogEntity(LocalDateTime.of(2023, 6, 15, 20, 26, 11), reservations.get(5), ReservationState.PAID),

                        new ReservationLogEntity(LocalDateTime.of(2023, 12, 13, 16, 17, 27), reservations.get(6), ReservationState.CREATED),

                        new ReservationLogEntity(LocalDateTime.of(2023, 12, 15, 5, 45, 22), reservations.get(7), ReservationState.CREATED)
                );
                logRepository.saveAll(logs);

                List<RoomEntity> availableRoomsBetweenDates = roomRepository.findAvailableRoomsBetweenDates(LocalDate.of(2023, 11, 20), LocalDate.of(2023, 11, 23));
                availableRoomsBetweenDates.forEach(System.out::println);
            }
        };
    }
}