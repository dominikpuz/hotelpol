package pl.edu.agh.to2.hotel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @SpyBean
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void shouldSaveNewCustomerAndGenerateId() {
        Customer notSaved = new Customer("Jan", "Kowalski", "123321123", "jankowalski@asd.das");
        Customer customer = customerService.addNewCustomer(notSaved);

        assertEquals(notSaved.getFirstName(), customer.getFirstName());
        assertEquals(notSaved.getLastName(), customer.getLastName());
        assertEquals(notSaved.getPhoneNumber(), customer.getPhoneNumber());
        assertEquals(notSaved.getEmail(), customer.getEmail());
        assertEquals(1, customer.getId());

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void shouldFindCustomerById() {
        Customer saved = customerService.addNewCustomer(new Customer("Jan", "Kowalski", "123321123", "jankowalski@asd.das"));

        Optional<Customer> found = customerService.findCustomerById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getPhoneNumber(), found.get().getPhoneNumber());
        assertEquals(saved.getEmail(), found.get().getEmail());
        assertEquals(saved.getFirstName(), found.get().getFirstName());
        assertEquals(saved.getLastName(), found.get().getLastName());
    }
}
