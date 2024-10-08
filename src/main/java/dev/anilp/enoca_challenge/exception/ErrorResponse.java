package dev.anilp.enoca_challenge.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String path,
        int statusCode,
        LocalDateTime timestamp,
        List<String> errors
) {
}
