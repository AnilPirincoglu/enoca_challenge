package dev.anilp.enoca_challenge.customer;

import dev.anilp.enoca_challenge.customer.util.dto.AddCustomerRequestDto;
import dev.anilp.enoca_challenge.exception.exceptions.DuplicateResourceException;
import dev.anilp.enoca_challenge.exception.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dev.anilp.enoca_challenge.customer.util.CustomerMapper.AddRequestToCustomer;
import static dev.anilp.enoca_challenge.exception.ErrorMessage.CUSTOMER_EMAIL_ALREADY_EXISTS;
import static dev.anilp.enoca_challenge.exception.ErrorMessage.CUSTOMER_NOT_FOUND_WITH_GIVEN_ID;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(AddCustomerRequestDto addCustomerRequestDto) {
        existsCustomerByEmail(addCustomerRequestDto.email());

        Customer customer = AddRequestToCustomer(addCustomerRequestDto);

        log.info("Adding customer: {}", addCustomerRequestDto);
        customerRepository.save(customer);
    }

    public Customer findCustomerById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND_WITH_GIVEN_ID, id.toString()));
    }

    private void existsCustomerByEmail(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(CUSTOMER_EMAIL_ALREADY_EXISTS, email);
        }
    }
}
