package pl.edu.agh.to2.hotel.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.to2.hotel.mapper.ModelEntityMapper;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerFilter;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;
import pl.edu.agh.to2.hotel.util.EmailValidator;
import pl.edu.agh.to2.hotel.util.PhoneNumberValidator;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final ModelEntityMapper modelEntityMapper;

    public CustomerService(CustomerRepository customerRepository, ModelEntityMapper modelEntityMapper) {
        this.customerRepository = customerRepository;
        this.modelEntityMapper = modelEntityMapper;
    }

    public List<Customer> searchCustomers(CustomerFilter filter) {
        return customerRepository.searchCustomers(filter, PageRequest.of(0, 20)).stream().map(modelEntityMapper::mapCustomerFromEntity).toList();
    }

    public Optional<Customer> findCustomerById(long id) {
        return customerRepository.findById(id).map(modelEntityMapper::mapCustomerFromEntity);
    }

    public Customer addNewCustomer(Customer customer) {
        return saveCustomer(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return saveCustomer(customer);
    }

    private Customer saveCustomer(Customer customer) {
        throwIfThereAreUnfulfilledFields(customer);
        throwIfEmailNotMatchesRegex(customer.getEmail());
        throwIfPhoneNumberNotMatchesRegex(customer.getPhoneNumber());
        CustomerEntity entity = customerRepository.save(modelEntityMapper.mapCustomerToEntity(customer));
        return modelEntityMapper.mapCustomerFromEntity(entity);
    }

    private void throwIfThereAreUnfulfilledFields(Customer customer) {
        if(customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            throw new IllegalOperationException("Customer first name cannot be empty!");
        }
        if(customer.getLastName() == null || customer.getLastName().isBlank()) {
            throw new IllegalOperationException("Customer last name cannot be empty!");
        }
        if(customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            throw new IllegalOperationException("Customer phone number cannot be empty!");
        }
        if(customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new IllegalOperationException("Customer email cannot be empty!");
        }
    }

    private void throwIfEmailNotMatchesRegex(String email) {
        if(!EmailValidator.isValidEmail(email)) {
            throw new IllegalOperationException("Customer email is not valid email!");
        }
    }

    private void throwIfPhoneNumberNotMatchesRegex(String phoneNumber) {
        if(!PhoneNumberValidator.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalOperationException("Customer phone number is not valid phone number!");
        }
    }
}