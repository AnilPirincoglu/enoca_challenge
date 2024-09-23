package dev.anilp.enoca_challenge.exceptions;

public enum ErrorMessage {
    EMAIL_ALREADY_EXISTS("Email already exists: "),
    PRODUCT_NOT_FOUND_WITH_GIVEN_ID("Product not found with given id: "),
    PRODUCT_ALREADY_EXISTS("Product already exists with name: ");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
