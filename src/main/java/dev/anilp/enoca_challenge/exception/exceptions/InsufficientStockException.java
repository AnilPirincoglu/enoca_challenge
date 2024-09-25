package dev.anilp.enoca_challenge.exception.exceptions;

import dev.anilp.enoca_challenge.exception.ErrorMessage;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(ErrorMessage message, Integer stock) {
        super(message.getMessage() + stock);
    }
}
