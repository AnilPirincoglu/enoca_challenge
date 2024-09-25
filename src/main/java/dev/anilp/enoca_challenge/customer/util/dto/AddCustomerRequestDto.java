package dev.anilp.enoca_challenge.customer.util.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AddCustomerRequestDto(
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,
        @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
        String lastName,
        @Email(message = "Email should be valid")
        @Size(max = 150, message = "Email must be less than 150 characters")
        String email
) {
}
