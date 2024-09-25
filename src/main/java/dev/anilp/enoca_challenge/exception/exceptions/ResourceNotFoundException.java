package dev.anilp.enoca_challenge.exception.exceptions;

import dev.anilp.enoca_challenge.exception.ErrorMessage;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(ErrorMessage message, String resource) {
        super(message.getMessage() + resource);
    }
}
