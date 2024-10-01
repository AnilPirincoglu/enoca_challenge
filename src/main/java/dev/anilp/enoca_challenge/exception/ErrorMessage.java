package dev.anilp.enoca_challenge.exception;

public enum ErrorMessage {
    CUSTOMER_EMAIL_ALREADY_EXISTS("Email already exists: "),
    CUSTOMER_NOT_FOUND_WITH_GIVEN_ID("Customer not found with given id: "),
    PRODUCT_NOT_FOUND_WITH_GIVEN_ID("Product not found with given id: "),
    PRODUCT_ALREADY_EXISTS("Product already exists with name: "),
    PRODUCT_NOT_FOUND_WITH_GIVEN_NAME("Product not found with given name: "),
    PRODUCT_NOT_IN_CART("Product not in your cart with given name: "),
    INSUFFICIENT_STOCK("Insufficient stock! Available stock: "),
    CART_NOT_FOUND_WITH_GIVEN_ID("Cart not found with given id: "),
    ORDER_NOT_FOUND_WITH_GIVEN_CODE("Order not found with given code: ");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
