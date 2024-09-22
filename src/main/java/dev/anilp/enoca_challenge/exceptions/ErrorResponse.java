package dev.anilp.enoca_challenge.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String path,
        int statusCode,
        String message,
        LocalDateTime timestamp,
        List<String> errors
) {
}
