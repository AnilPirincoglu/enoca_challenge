package dev.anilp.enoca_challenge.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(ErrorMessage message, String resource) {
        super(message.getMessage() + resource);
    }
}
