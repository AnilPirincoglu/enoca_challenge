package dev.anilp.enoca_challenge.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);
    private static final String VALIDATION_FAILED = "Validation Failed";

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handle(DuplicateResourceException e, HttpServletRequest request) {

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.CONFLICT, e.getMessage(), List.of());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e, HttpServletRequest request) {

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.NOT_FOUND, e.getMessage(), List.of());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<String> errors = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.BAD_REQUEST, VALIDATION_FAILED, errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("Error Name : {}", e.getClass().getSimpleName());

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), List.of());

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ErrorResponse createErrorResponse(HttpServletRequest request, HttpStatus httpStatus, String message, List<String> errors) {
        return new ErrorResponse(
                request.getRequestURI(),
                httpStatus.value(),
                message,
                LocalDateTime.now(),
                errors
        );
    }
}
