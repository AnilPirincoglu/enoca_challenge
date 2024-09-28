package dev.anilp.enoca_challenge.exception;

import dev.anilp.enoca_challenge.exception.exceptions.DuplicateResourceException;
import dev.anilp.enoca_challenge.exception.exceptions.InsufficientStockException;
import dev.anilp.enoca_challenge.exception.exceptions.ResourceNotFoundException;
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
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handle(InsufficientStockException e, HttpServletRequest request) {

        List<String> errors = List.of(e.getMessage());

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handle(DuplicateResourceException e, HttpServletRequest request) {

        List<String> errors = List.of(e.getMessage());

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.CONFLICT, errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e, HttpServletRequest request) {

        List<String> errors = List.of(e.getMessage());

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.NOT_FOUND, errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<String> errors = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("Error Name : {}, Stack-Trace : {}", e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()).replace(",", "\n"));

        List<String> errors = List.of(e.getMessage());

        ErrorResponse errorResponse = createErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, errors);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ErrorResponse createErrorResponse(HttpServletRequest request, HttpStatus httpStatus, List<String> errors) {
        return new ErrorResponse(
                request.getRequestURI(),
                httpStatus.value(),
                LocalDateTime.now(),
                errors
        );
    }
}
