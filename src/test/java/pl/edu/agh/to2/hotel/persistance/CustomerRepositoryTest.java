package pl.edu.agh.to2.hotel.persistance;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import pl.edu.agh.to2.hotel.TestUtils;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerFilter;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerEntity janKowalski, nataliaNowak, aleksandraKrawiec, alexRyba;

    @BeforeEach
    public void setup() {
        janKowalski = TestUtils.createCustomerEntity1();
        nataliaNowak = TestUtils.createCustomerEntity2();
        aleksandraKrawiec = new CustomerEntity("Aleksandra", "Krawiec", "124763282", "akrawiec@wp.pl");
        alexRyba = new CustomerEntity("Alex", "Ryba", "123146928", "alex123@gmail.com");
    }

    @Test
    public void shouldSaveCustomerEntity() {
        customerRepository.save(janKowalski);

        assertEquals(1, customerRepository.count());
        assertTrue(customerRepository.findAll().contains(janKowalski));
    }

    @Test
    public void shouldFindCustomerByFirstNameAndLastName() {
        customerRepository.save(nataliaNowak);
        customerRepository.save(janKowalski);

        Optional<CustomerEntity> found = customerRepository.findCustomerByFirstNameAndLastName_IgnoreCase("Jan", "Kowalski");

        assertTrue(found.isPresent());
        assertEquals(found.get(), janKowalski);
    }

    @Test
    public void shouldFindCustomerByNamesIgnoreCase() {
        customerRepository.save(nataliaNowak);
        customerRepository.save(janKowalski);

        Optional<CustomerEntity> found = customerRepository.findCustomerByFirstNameAndLastName_IgnoreCase("JaN", "KoWALski");

        assertTrue(found.isPresent());
        assertEquals(found.get(), janKowalski);
    }

    @Test
    public void shouldNotFindCustomerByNames() {
        customerRepository.save(nataliaNowak);
        customerRepository.save(janKowalski);

        Optional<CustomerEntity> found = customerRepository.findCustomerByFirstNameAndLastName_IgnoreCase("Not", "Existing");
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindCustomerByEmailIgnoreCase() {
        customerRepository.save(nataliaNowak);
        customerRepository.save(janKowalski);

        Optional<CustomerEntity> found = customerRepository.findCustomerByEmailEqualsIgnoreCase("JanKOWALSKI@gmail.COM");
        assertTrue(found.isPresent());
        assertEquals(found.get(), janKowalski);
    }

    private void prepareCustomerFilterTest() {
        customerRepository.save(nataliaNowak);
        customerRepository.save(janKowalski);
        customerRepository.save(aleksandraKrawiec);
        customerRepository.save(alexRyba);
    }

    @Test
    public void shouldFindCustomersByFirstName() {
        prepareCustomerFilterTest();
        CustomerFilter filter = CustomerFilter.builder().firstName("Ale").build();

        List<CustomerEntity> customers = customerRepository.searchCustomers(filter, PageRequest.of(0, 10));

        assertEquals(2, customers.size());
        assertTrue(customers.contains(aleksandraKrawiec));
        assertTrue(customers.contains(alexRyba));
    }

    @Test
    public void shouldFindCustomersByLastName() {
        prepareCustomerFilterTest();
        CustomerFilter filter = CustomerFilter.builder().lastName("Ryb").build();

        List<CustomerEntity> customers = customerRepository.searchCustomers(filter, PageRequest.of(0, 10));

        assertEquals(1, customers.size());
        assertTrue(customers.contains(alexRyba));
    }

    @Test
    public void shouldFindCustomersByPhone() {
        prepareCustomerFilterTest();
        CustomerFilter filter = CustomerFilter.builder().phone("124").build();

        List<CustomerEntity> customers = customerRepository.searchCustomers(filter, PageRequest.of(0, 10));

        assertEquals(1, customers.size());
        assertTrue(customers.contains(aleksandraKrawiec));
    }

    @Test
    public void shouldFindCustomersByEmail() {
        prepareCustomerFilterTest();
        CustomerFilter filter = CustomerFilter.builder().email("alex123").build();

        List<CustomerEntity> customers = customerRepository.searchCustomers(filter, PageRequest.of(0, 10));

        assertEquals(1, customers.size());
        assertTrue(customers.contains(alexRyba));
    }

    @Test
    public void shouldFindCustomersByFirstNameAndPhone() {
        prepareCustomerFilterTest();
        CustomerFilter filter = CustomerFilter.builder().firstName("Ale").phone("123").build();

        List<CustomerEntity> customers = customerRepository.searchCustomers(filter, PageRequest.of(0, 10));

        assertEquals(1, customers.size());
        assertTrue(customers.contains(alexRyba));
    }

}
