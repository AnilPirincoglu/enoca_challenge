package dev.anilp.enoca_challenge.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(ErrorMessage message, String resource) {
        super(message.getMessage() + resource);
    }
}
