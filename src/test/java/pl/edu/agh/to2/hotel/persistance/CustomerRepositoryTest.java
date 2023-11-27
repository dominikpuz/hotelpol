package pl.edu.agh.to2.hotel.persistance;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void shouldSaveCustomerEntity() {
        customerRepository.save(TestUtils.sampleCustomerEntity1);

        assertEquals(1, customerRepository.count());
        assertTrue(customerRepository.findAll().contains(TestUtils.sampleCustomerEntity1));
    }

    @Test
    public void shouldFindCustomerByFirstNameAndLastName() {
        customerRepository.save(TestUtils.sampleCustomerEntity2);
        customerRepository.save(TestUtils.sampleCustomerEntity1);

        Optional<CustomerEntity> found = customerRepository.findCustomerByFirstNameAndLastName_IgnoreCase("Jan", "Kowalski");

        assertTrue(found.isPresent());
        assertEquals(found.get(), TestUtils.sampleCustomerEntity1);
    }

    @Test
    public void shouldFindCustomerByNamesIgnoreCase() {
        customerRepository.save(TestUtils.sampleCustomerEntity2);
        customerRepository.save(TestUtils.sampleCustomerEntity1);

        Optional<CustomerEntity> found = customerRepository.findCustomerByFirstNameAndLastName_IgnoreCase("JaN", "KoWALski");

        assertTrue(found.isPresent());
        assertEquals(found.get(), TestUtils.sampleCustomerEntity1);
    }

    @Test
    public void shouldNotFindCustomerByNames() {
        customerRepository.save(TestUtils.sampleCustomerEntity1);
        customerRepository.save(TestUtils.sampleCustomerEntity2);

        Optional<CustomerEntity> found = customerRepository.findCustomerByFirstNameAndLastName_IgnoreCase("Not", "Existing");
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindCustomerByEmailIgnoreCase() {
        customerRepository.save(TestUtils.sampleCustomerEntity1);
        customerRepository.save(TestUtils.sampleCustomerEntity2);

        Optional<CustomerEntity> found = customerRepository.findCustomerByEmailEqualsIgnoreCase("JanKOWALSKI@gmail.COM");
        assertTrue(found.isPresent());
        assertEquals(found.get(), TestUtils.sampleCustomerEntity1);
    }
}
