package pl.edu.agh.to2.hotel.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.to2.hotel.mapper.ModelEntityMapper;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerEntity;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerFilter;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerRepository;

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

    private Customer saveCustomer(Customer customer) {
        CustomerEntity entity = customerRepository.save(modelEntityMapper.mapCustomerToEntity(customer));
        return modelEntityMapper.mapCustomerFromEntity(entity);
    }

    public Customer addNewCustomer(Customer customer) {
        return saveCustomer(customer);
    }
}