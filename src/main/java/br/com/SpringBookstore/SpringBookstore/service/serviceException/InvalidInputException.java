package br.com.SpringBookstore.SpringBookstore.service.serviceException;

/**
 * Exceção para entradas inválidas fornecidas pelo cliente.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}