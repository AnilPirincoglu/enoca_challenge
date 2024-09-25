package dev.anilp.enoca_challenge.exception.exceptions;

import dev.anilp.enoca_challenge.exception.ErrorMessage;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(ErrorMessage message, String resource) {
        super(message.getMessage() + resource);
    }
}
