package dev.anilp.enoca_challenge.customer.util;

import dev.anilp.enoca_challenge.customer.Customer;
import dev.anilp.enoca_challenge.customer.util.dto.AddCustomerRequestDto;

public class CustomerMapper {

    public static Customer AddRequestToCustomer(AddCustomerRequestDto addCustomerRequestDTO) {
        return new Customer(addCustomerRequestDTO.name(), addCustomerRequestDTO.lastName(), addCustomerRequestDTO.email());
    }
}
