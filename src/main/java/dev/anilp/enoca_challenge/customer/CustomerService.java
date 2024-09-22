package dev.anilp.enoca_challenge.customer;

import dev.anilp.enoca_challenge.exceptions.DuplicateResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.anilp.enoca_challenge.customer.CustomerMapper.AddRequestToCustomer;
import static dev.anilp.enoca_challenge.exceptions.ErrorMessage.EMAIL_ALREADY_EXISTS;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(AddCustomerRequestDto request) {

        if (customerRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(EMAIL_ALREADY_EXISTS, request.email());
        }

        Customer customer = AddRequestToCustomer(request);

        log.info("Adding customer: {}", request);
        customerRepository.save(customer);
    }
}
