package pl.edu.agh.to2.hotel.persistance.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findCustomerByEmailEqualsIgnoreCase(String email);

    @Query("SELECT C FROM CustomerEntity C WHERE C.firstName ILIKE :firstName AND C.lastName ILIKE :lastName")
    Optional<CustomerEntity> findCustomerByFirstNameAndLastName_IgnoreCase(String firstName, String lastName);
}
