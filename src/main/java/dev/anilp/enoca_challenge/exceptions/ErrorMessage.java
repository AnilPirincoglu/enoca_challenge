package dev.anilp.enoca_challenge.exceptions;

public enum ErrorMessage {
    EMAIL_ALREADY_EXISTS("Email already exists: ");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
