package br.com.SpringBookstore.SpringBookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Manipulador global de exceções, lidando com erros na API REST.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura exceções genéricas e retorna 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                new Date(),
                "Ocorreu um erro inesperado: " + ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Captura exceções de entrada inválida e retorna 400 Bad Request.
     */
    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura exceções de lógica de negócios e retorna 400 Bad Request.
     */
    @ExceptionHandler(BusinessLogicException.class)
    public final ResponseEntity<ErrorResponse> handleBusinessLogicException(BusinessLogicException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura exceções de conflito e retorna 409 Conflict.
     */
    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}