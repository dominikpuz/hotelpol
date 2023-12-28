package pl.edu.agh.to2.hotel.persistance.customer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.to2.hotel.model.filters.CustomerFilter;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findCustomerByEmailEqualsIgnoreCase(String email);

    @Query("SELECT C FROM CustomerEntity C WHERE C.firstName ILIKE :firstName AND C.lastName ILIKE :lastName")
    Optional<CustomerEntity> findCustomerByFirstNameAndLastName_IgnoreCase(String firstName, String lastName);

    @Query("SELECT c FROM CustomerEntity c WHERE " +
            "(:#{#filter.firstName} IS NULL OR c.firstName ILIKE :#{#filter.firstName()}%) " +
            "AND (:#{#filter.lastName} IS NULL OR c.lastName ILIKE :#{#filter.lastName()}%) " +
            "AND (:#{#filter.phone} IS NULL OR c.phoneNumber LIKE %:#{#filter.phone()}%) " +
            "AND (:#{#filter.email} IS NULL OR c.email LIKE :#{#filter.email()}%)")
    List<CustomerEntity> searchCustomers(@Param("filter") CustomerFilter filter, Pageable pageable);
}
