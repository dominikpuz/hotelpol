package pl.edu.agh.to2.hotel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
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

    @ParameterizedTest
    @MethodSource("validationArgumentsProvider")
    public void shouldNotAddNewCustomerDueToValidation(Customer customer, String validationMessage) {
        var exception = assertThrows(IllegalOperationException.class, () -> customerService.addNewCustomer(customer));
        assertEquals(validationMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validationArgumentsProvider")
    public void shouldNotEditCustomerDueToValidation(Customer customer, String validationMessage) {
        customerService.addNewCustomer(new Customer("Jan", "Kowalski", "123321123", "jankowalski@asd.das"));

        var exception = assertThrows(IllegalOperationException.class, () -> customerService.updateCustomer(customer));
        assertEquals(validationMessage, exception.getMessage());
    }

    public static Stream<Arguments> validationArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new Customer(null, "Kowalski", "123321123", "jankowalski@asd.das"),
                        "Customer first name cannot be empty!"
                ),
                Arguments.of(
                        new Customer(" ", "Kowalski", "123321123", "jankowalski@asd.das"),
                        "Customer first name cannot be empty!"
                ),
                Arguments.of(
                        new Customer("Jan", null, "123321123", "jankowalski@asd.das"),
                        "Customer last name cannot be empty!"
                ),
                Arguments.of(
                        new Customer("Jan", "     ", "123321123", "jankowalski@asd.das"),
                        "Customer last name cannot be empty!"
                ),
                Arguments.of(
                        new Customer("Jan", "Kowalski", null, "jankowalski@asd.das"),
                        "Customer phone number cannot be empty!"
                ),
                Arguments.of(
                        new Customer("Jan", "Kowalski", "", "jankowalski@asd.das"),
                        "Customer phone number cannot be empty!"
                ),
                Arguments.of(
                        new Customer("Jan", "Kowalski", "321-123", "jankowalski@asd.das"),
                        "Customer phone number is not valid phone number!"
                ),
                Arguments.of(
                        new Customer("Jan", "Kowalski", "123321123", null),
                        "Customer email cannot be empty!"
                ),
                Arguments.of(
                        new Customer("Jan", "Kowalski", "123321123", " "),
                        "Customer email cannot be empty!"
                ),
                Arguments.of(
                        new Customer("Jan", "Kowalski", "123321123", "jankowalski@asd@Asd@onet.pl"),
                        "Customer email is not valid email!"
                )
        );
    }
}
