package pl.edu.agh.to2.hotel.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationEntity;
import pl.edu.agh.to2.hotel.persistance.reservation.ReservationRepository;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;
import pl.edu.agh.to2.hotel.persistance.room.RoomEntity;
import pl.edu.agh.to2.hotel.persistance.room.RoomRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("dev")
public class Configurator {

    @Bean
    CommandLineRunner commandLineRunner(RoomRepository roomRepository, CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        return args -> {
            if (roomRepository.count() == 0) {
                RoomEntity room = new RoomEntity("202A", 2, Arrays.asList(BedType.SINGLE_BED, BedType.DOUBLE_BED), RoomStandard.EXCLUSIVE, 200.0);
                roomRepository.save(room);
                RoomEntity room2 = new RoomEntity("305", 3, Arrays.asList(BedType.SINGLE_BED, BedType.SINGLE_BED), RoomStandard.CLASSIC, 130.0);
                roomRepository.save(room2);

                CustomerEntity customer = new CustomerEntity("Jan", "Kowalski", "123321345", "jankowalski@gmail.com");
                customerRepository.save(customer);
                CustomerEntity customer2 = new CustomerEntity("Natalia", "Wrona", "+48654982032", "natalia123@wp.pl");
                customerRepository.save(customer2);

                ReservationEntity reservation = new ReservationEntity(room, customer, LocalDate.of(2023, 11, 25), LocalDate.of(2023, 11, 30));
                reservationRepository.save(reservation);

                ReservationEntity reservation1 = new ReservationEntity(room, customer2, LocalDate.of(2023, 11, 20), LocalDate.of(2023, 11, 27));
                reservationRepository.save(reservation1);

                List<RoomEntity> availableRoomsBetweenDates = roomRepository.findAvailableRoomsBetweenDates(LocalDate.of(2023, 11, 20), LocalDate.of(2023, 11, 23));
                availableRoomsBetweenDates.forEach(System.out::println);
            }
        };
    }
}