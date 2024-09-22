package dev.anilp.enoca_challenge.customer;

public class CustomerMapper {

    public static Customer AddRequestToCustomer(AddCustomerRequestDto addCustomerRequestDTO) {
        return new Customer(addCustomerRequestDTO.name(), addCustomerRequestDTO.lastName(), addCustomerRequestDTO.email());
    }
}
